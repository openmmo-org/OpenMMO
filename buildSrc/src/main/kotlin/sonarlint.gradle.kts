package buildsrc.convention

plugins { id("name.remal.sonarlint") }

sonarLint {
  languages { include("kotlin", "java") }
  rules {
    disable("kotlin:S1186")
    disable("kotlin:S3776")
  }
}
