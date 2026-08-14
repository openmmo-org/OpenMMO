package de.fiereu.openmmo.launcher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun LauncherWindow(
    state: LauncherUiState,
    onPlay: () -> Unit,
    onVerify: () -> Unit,
) {
  MaterialTheme(colorScheme = darkColorScheme()) {
    Surface(modifier = Modifier.fillMaxSize()) {
      Column(
          modifier = Modifier.fillMaxSize().padding(24.dp),
          verticalArrangement = Arrangement.SpaceBetween,
      ) {
        Column {
          Text("OpenMMO", style = MaterialTheme.typography.headlineMedium)
          Spacer(Modifier.height(4.dp))
          Text(
              "An open server for PokeMMO",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
          if (state.error != null) {
            Text(
                "Error",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                state.error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
          } else {
            Text(state.status, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                state.detail,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
          }
          Spacer(Modifier.height(12.dp))
          if (state.busy) {
            val progress = state.progress
            if (progress == null) {
              LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
              LinearProgressIndicator(
                  progress = { progress },
                  modifier = Modifier.fillMaxWidth(),
              )
            }
          }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
          OutlinedButton(onClick = onVerify, enabled = !state.busy) { Text("Verify and repair") }
          Spacer(Modifier.width(12.dp))
          Button(onClick = onPlay, enabled = !state.busy) { Text("Play") }
        }
      }
    }
  }
}
