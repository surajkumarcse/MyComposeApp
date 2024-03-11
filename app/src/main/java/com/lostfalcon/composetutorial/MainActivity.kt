package com.lostfalcon.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lostfalcon.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Author(val firstName: String, val lastName: String)
data class Message2(val author: Author, val body: String)
data class Message(val author: String, val body: String)

@Composable
fun Greeting(author: Author, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { /* Ignoring onClick */ }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_fitness_center_24),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Hello",
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )

            Spacer(modifier = Modifier
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
            )

            Row() {
                Text(
                    text = "${author.firstName}!",
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )

                Spacer(modifier = Modifier
                    .height(4.dp)
                    .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = "${author.lastName}",
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.baseline_fitness_center_24),
            contentDescription = "Image of Author",
            modifier = Modifier
                .clip(CircleShape)
                .padding(4.dp)
        )

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }) {
            Text(
                text = msg.author,
                modifier = Modifier.padding(4.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Surface(shape = MaterialTheme.shapes.medium,
                modifier = Modifier.animateContentSize().padding(1.dp),
                color = surfaceColor,
                shadowElevation = 1.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                    )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message -> 
            MessageCard(msg = message)
        }
    }
}

@Preview(name = "Messages")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode conversation"
)
@Composable
fun messages() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MessageCardPreview() {
    ComposeTutorialTheme {
        MessageCard(Message("John", "Hey buddy, can you help me here?"))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTutorialTheme {
        Greeting(Author("Android", "Developer"))
    }
}