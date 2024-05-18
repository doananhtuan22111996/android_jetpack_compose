package vn.root.android_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import vn.root.android_jetpack_compose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			AppTheme {
				Scaffold(
					modifier = Modifier.fillMaxSize()
				) { innerPadding ->
					MyApp(Modifier.padding(innerPadding))
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
	var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
	Surface(modifier = modifier) {
		if (shouldShowOnboarding) {
			OnBoardingScreen(onClick = {
				shouldShowOnboarding = false
			})
		} else {
			GreetingScreen()
		}
	}
}

@Composable
private fun OnBoardingScreen(onClick: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Welcome to the Basics Codelab!", modifier = Modifier.padding(16.dp))
		Button(onClick = onClick) {
			Text(text = "Continue")
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun GreetingScreen(names: List<String> = List(1000) { "$it" }) {
	LazyColumn(
		modifier = Modifier.fillMaxWidth()
	) {
		items(items = names) { name ->
			Greeting(
				name = name, modifier = Modifier.padding(16.dp)
			)
			
		}
	}
}

@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
	var expanded by rememberSaveable { mutableStateOf(false) }
//	val animFloat by animateFloatAsState(
//		targetValue = if (expanded) painterResource(R.drawable.baseline_expand_more_24) else painterResource(
//			id = R.drawable.baseline_expand_less_24
//		), typeConverter = TwoWayConverter(
//			convertFromVector = R.drawable.baseline_expand_more_24,
//			convertToVector = R.drawable.baseline_expand_less_24
//		), label = "animateValueAsState"
//	)
	
	Surface(
		color = MaterialTheme.colorScheme.primary, modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp)
	) {
		Column(
			modifier = modifier.animateContentSize(
				animationSpec = spring(
					dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
				)
			)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(text = "Hello $name!")
				IconButton(onClick = {
					expanded = !expanded
				}) {
					AnimatedContent(
						targetState = if (expanded) painterResource(R.drawable.baseline_expand_more_24) else painterResource(
							id = R.drawable.baseline_expand_less_24
						), transitionSpec = {
							fadeIn(animationSpec = tween(800)) togetherWith fadeOut(
								animationSpec = tween(800)
							)
						}, label = "AnimatedContent"
					) { targetState ->
						Icon(
							painter = targetState,
							contentDescription = stringResource(R.string.icon),
						)
					}
				}
			}
			if (expanded) {
				Text(
					text = ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy. ").repeat(
						4
					), modifier = Modifier.padding(vertical = 8.dp)
				)
			}
		}
	}
}
