package buildsrc.convention

plugins { id("name.remal.sonarlint") }

sonarLint {
  languages { include("kotlin", "java") }
  rules {
    disable("kotlin:S1186")
    disable("kotlin:S3776")
    // The todo-to-issue workflow already tracks open tasks, so lint must not flag them too.
    disable("kotlin:S1135")
  }
}
