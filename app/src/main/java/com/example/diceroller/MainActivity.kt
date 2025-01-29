package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    ButtonandImage(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    )
}

@Composable
fun ButtonandImage(modifier: Modifier = Modifier) {

    var result by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    val diceImages = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6

    )

    val rotation by animateFloatAsState(
        targetValue = if (isRolling) 760f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )

    val scale by animateFloatAsState(
        targetValue = if (isRolling) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = diceImages[result - 1]),
                contentDescription = result.toString(),
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale)
                    .rotate(rotation)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isRolling) {
                        isRolling = true
                        result = (1..6).random()
                        //simulate rolling display
                        kotlinx.coroutines.MainScope().launch {
                            kotlinx.coroutines.delay(500)
                            isRolling = false
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 200.dp, height = 60.dp) //Increased Size
                    .shadow(6.dp, shape = RoundedCornerShape(16.dp))

            ) {
                Text(
                    text = if (isRolling) "Rolling..." else stringResource(id = R.string.roll),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold

                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier,
                text = "The dice says your fate is $result"
            )

        }
    }

}












