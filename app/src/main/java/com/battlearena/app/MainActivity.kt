package com.battlearena.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Tournament(
    val id: Int, val name: String, val map: String,
    val mode: String, val fee: Int, val prize: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { BattleArenaApp() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BattleArenaApp() {
    var coins by remember { mutableStateOf(100) }
    val joined = remember { mutableStateListOf<Int>() }
    val tournaments = listOf(
        Tournament(1, "Bermuda Solo Cup", "Bermuda", "Solo", 10, 100),
        Tournament(2, "Purgatory Duo Clash", "Purgatory", "Duo", 20, 250),
        Tournament(3, "Kalahari Squad War", "Kalahari", "Squad", 40, 600),
        Tournament(4, "Alpine TDM 4v4", "Alpine", "TDM 4v4", 15, 150)
    )
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Battle Arena") },
            actions = { Text("Coins: $coins", Modifier.padding(end = 16.dp)) }
        )
    }) { pad ->
        LazyColumn(
            Modifier.padding(pad).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tournaments) { t ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp)) {
                        Text(t.name, style = MaterialTheme.typography.titleMedium)
                        Text("${t.map} | ${t.mode}")
                        Text("Entry: ${t.fee} coins | Prize: ${t.prize} coins")
                        Spacer(Modifier.height(8.dp))
                        Button(
                            enabled = t.id !in joined && coins >= t.fee,
                            onClick = { coins -= t.fee; joined.add(t.id) }
                        ) {
                            Text(if (t.id in joined) "Joined" else "Join")
                        }
                    }
                }
            }
        }
    }
}
