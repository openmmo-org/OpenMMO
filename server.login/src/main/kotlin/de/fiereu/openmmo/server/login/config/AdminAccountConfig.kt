package de.fiereu.openmmo.server.login.config

private const val USERNAME_COLUMN_LENGTH = 32

data class AdminAccountConfig(val username: String, val password: String) {
  init {
    require(username.isNotBlank()) { "server.admin.username must not be blank" }
    require(username.length <= USERNAME_COLUMN_LENGTH) {
      "server.admin.username is longer than $USERNAME_COLUMN_LENGTH characters"
    }
    require(password.isNotBlank()) { "server.admin.password must not be blank" }
  }

  // Without this the generated one puts the password in any log line that prints the config.
  override fun toString(): String = "AdminAccountConfig(username=$username)"
}
