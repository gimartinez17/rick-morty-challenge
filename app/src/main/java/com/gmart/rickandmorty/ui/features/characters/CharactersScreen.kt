package com.gmart.rickandmorty.ui.features.characters

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gmart.domain.entities.Origin
import com.gmart.domain.entities.RnMCharacter
import com.gmart.rickandmorty.R
import com.gmart.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharactersScreen(
    paddingValues: PaddingValues,
    viewModel: CharactersViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(CharactersEvent.LoadCharacters)
    }

    when {
        state.isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }

        state.error != null -> ErrorMessage(message = state.error!!)
        else -> CharactersContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            list = state.characters
        )
    }
}

/**
 * A composable that displays a list of characters.
 *
 * @param modifier Modifier for layout adjustments.
 * @param list List of characters to display.
 */
@Composable
fun CharactersContent(
    modifier: Modifier = Modifier,
    list: List<RnMCharacter>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list.size) { index ->
            val character = list[index]
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Card(
                        shape = RoundedCornerShape(8.0.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.size(80.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(character.image)
                                .build(),
                            contentDescription = "Character Image",
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.placeholder)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(
                            character.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            character.origin.name,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Row {
                            StatusDot(
                                status = character.status,
                                modifier = Modifier
                                    .size(8.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                character.status,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * A composable that shows a colored dot indicating the character's status.
 *
 * @param status Status of the character (alive, dead, unknown).
 * @param modifier Modifier for layout adjustments.
 */
@Composable
fun StatusDot(status: String, modifier: Modifier = Modifier) {
    val color = when (status.lowercase()) {
        "alive" -> Color.Green
        "dead" -> Color.Red
        else -> Color.Gray
    }

    Box(
        modifier = modifier
            .size(12.dp)
            .background(color, shape = CircleShape)
    )
}

/**
 * A composable that displays an error message in the center of the screen.
 *
 * @param modifier Modifier for layout adjustments.
 * @param message The error message to display.
 */
@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Error Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Surface {
            CharactersContent(
                list = listOf(
                    RnMCharacter(
                        id = 1,
                        name = "Rick Sanchez",
                        status = "Alive",
                        species = "Human",
                        gender = "Male",
                        origin = Origin(
                            name = "Earth (C-137)",
                            url = "https://rickandmortyapi.com/api/location/1"
                        ),
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                    ),
                )
            )
        }
    }
}
