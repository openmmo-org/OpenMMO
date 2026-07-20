# Writing a code generator

OpenMMO builds most of its game data (maps, moves, species, …) at compile time.
The `:codegen` module reads the raw data files from the pret decomps under
`decomp/` and turns them into Kotlin `*Def` objects. Those generated files are
**not committed**. They are regenerated on every build.

This guide shows how to add a new generator.

## How it fits together

```
decomp/pokeemerald/...           the raw C data (git submodule)
        │  parsed by
        ▼
codegen/src/generator/kotlin/    the generator (build-time only)
        │  renders via a JTE template
        ▼
codegen/build/generated/...      the generated Kotlin (throwaway)
        │  compiled into
        ▼
codegen/src/main/kotlin/         the runtime Def + Registry the server uses
```

Each generator is registered in `codegen/build.gradle.kts` and runs as its own
Gradle task (`generateMaps`, `generateMoves`, `generatePokemon`, …).

## Pick a data source first

There are two ways to choose which decomp to read from:

- **By region:** for data that is different in each region, like maps. Hoenn
  maps come from `pokeemerald`, Kanto maps from `pokefirered`. The generator gets
  one `region|path` argument per region.
- **Single source of truth:** for data that is the same across games, like moves
  and species. We just read one decomp (`pokeemerald`). Where the two games
  disagree on a detail (a wild held item, a flee rate) we accept the values from
  that one decomp instead of trying to merge them.

Rule of thumb: if the same thing (a move, a Pokémon) exists in both games, use a
single source. If the thing belongs to one region, use by-region.

> **Note:** the NDS decomps (`pokeblack`, `pokeheartgold`, `pokeplatinum`) are
> **not implemented yet**. They use 3D maps and a different data format, so only
> the GBA decomps (`pokeemerald`, `pokefirered`) are wired up today.

## The pieces of a generator

Say you want to generate `berries`. You add:

| File | Job |
|---|---|
| `codegen/src/generator/kotlin/de/fiereu/openmmo/codegen/berry/Main.kt` | Entry point: reads args, runs the parser, calls the renderer |
| `.../berry/BerryParser.kt` | Reads the decomp files and returns a list of `ParsedBerry` |
| `.../berry/ParsedModel.kt` | The `ParsedBerry` data class (plain values + enum references as `String`) |
| `.../berry/Constants.kt` | Small helpers that turn a decomp name into an enum reference |
| `.../berry/RenderUtil.kt` | Turns one `ParsedBerry` into a line of Kotlin source |
| `.../berry/BerryRenderer.kt` | Runs the JTE template and writes the output file |
| `codegen/src/generator/jte/berry/BerryRegistry.jte` | The template for the generated file |
| `codegen/src/main/kotlin/de/fiereu/openmmo/berry/BerryDef.kt` | The runtime type the server uses |
| `.../berry/BerryRegistry.kt` | Loads the generated data and looks it up by id |
| `common/.../enums/*.kt` | Any semantic enums the data needs |

Then you register it in `codegen/build.gradle.kts`.

## Turn numbers into enums

The netcode is the source of truth, so raw integers from the decomp should become
real enums. For example, a move's `type` byte becomes a `PokemonType`, and a
move's `target` becomes a `MoveTarget`.

- If the netcode already has an enum (like `PokemonType`), reuse it.
- If it doesn't, add a small hand-written enum in `common` (like `MoveTarget`,
  `EggGroup`). For long lists you can generate the enum body once from the decomp
  and commit it (that is how `MoveEffect` and `Ability` were made).

The parser stores enum values as **strings** (e.g. `"PokemonType.GRASS"`) and the
template just prints them, so the generator never depends on the runtime types.

## Example: reference an enum

`Constants.kt` turns a decomp token into an enum reference:

```kotlin
fun typeRef(token: String): String {
  val name = token.removePrefix("TYPE_")
  val mapped = if (name == "MYSTERY") "QUESTIONQUESTIONQUESTION" else name
  return "PokemonType.$mapped"
}
```

`RenderUtil.kt` builds one line of generated Kotlin:

```kotlin
fun move(m: ParsedMove): String =
    "MoveDef(id = ${m.id}, name = ${escapeString(m.name)}," +
        " type = ${m.type}, power = ${m.power})"
```

The template loops over the parsed items and calls that:

```
@for(ParsedMove m : chunks.get(c))
    reg.register(${RenderUtil.INSTANCE.move(m)})
@endfor
```

## Register the generator

In `codegen/build.gradle.kts`, add one `register(...)` block. The name becomes the
task name (`generateBerries`) and the template folder (`src/generator/jte/berry`
by default):

```kotlin
jteCodegen {
  register("berry") {
    mainClass.set("de.fiereu.openmmo.codegen.berry.Main")
    inputDirs.from(sourceDecompDir)            // what to watch for changes
    extraArgs.set(listOf(sourceDecompDir.asFile.absolutePath))
  }
}
```

`Main` receives four arguments in order: the output directory, the templates
directory, the JTE cache directory, and then your `extraArgs` (here, the decomp
path). By-region generators pass one `region|path` string per region instead.
