package com.payamgr.androidbenchmark.ui.page.calculator

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.util.toRange
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.payamgr.androidbenchmark.data.model.NumType
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.data.state.CalculatorState
import com.payamgr.androidbenchmark.ui.theme.AndroidBenchmarkTheme
import kotlin.math.roundToInt

//@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CalculatorPreview() {
    AndroidBenchmarkTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Mavericks.initialize(LocalContext.current)
            CalculatorView.Page(viewModel = CalculatorVMImpl(CalculatorState()))
        }
    }
}

object CalculatorView {
    private val choiceSize = 40.dp

    @Composable
    fun Page(viewModel: CalculatorVM = mavericksViewModel()) {
        val state by viewModel.collectAsState()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.testTag("CalculatorView"),
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    state.apply {
                        NumRow("Num 1", -10f..10f, num1) {
                            viewModel.onNumChanged(
                                NumType.Num1,
                                it
                            )
                        }
                        Space()
                        MultiChoice(Operation.values(), operation, viewModel::onOperationChanged)
                        Space()
                        NumRow("Num 2", -10f..10f, num2) {
                            viewModel.onNumChanged(
                                NumType.Num2,
                                it
                            )
                        }
                        Space()
                        Text(result,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Result" })
                    }
                }
            }
        }
    }

    @Composable
    fun NumRow(
        label: String,
        range: ClosedFloatingPointRange<Float>,
        value: Int,
        onValueChange: (Int) -> Unit
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val shape = RoundedCornerShape(8.dp)
            Text(
                value.toString(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(48.dp)
                    .padding(end = 8.dp)
                    .shadow(2.dp, shape = shape)
                    .clip(shape)
                    .background(color = Color.hsl(0f, 0f, .9f))
                    .padding(8.dp)
                    .semantics { contentDescription = "Slider $label, Value: $value" }
            )
            Slider(
                value = value.toFloat(),
                valueRange = range,
                steps = range.toRange().run { upper - lower }.roundToInt(),
                onValueChange = { onValueChange(it.toInt()) },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF006400),
                    activeTrackColor = Color.Red,
                    activeTickColor = Color.White,
                    inactiveTickColor = Color.White,
                ),
                modifier = Modifier.semantics { contentDescription = "Slider $label" }
            )
        }
    }

    @Composable
    private fun Space() {
        Spacer(modifier = Modifier.size(16.dp))
    }

    @Composable
    fun MultiChoice(
        choices: Array<Operation>,
        selected: Operation,
        onSelected: (Operation) -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            choices.forEach { Choice(it, it == selected) { onSelected(it) } }
        }
    }

    @Composable
    fun Choice(operation: Operation, isSelected: Boolean, onClick: () -> Unit) {
        val context = LocalContext.current
        ElevatedButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = if (isSelected) 0.dp else 4.dp
            ),
            modifier = Modifier
                .size(choiceSize)
                .clearAndSetSemantics {
                    contentDescription = context.getString(operation.descriptionId)
                    set(SemanticsProperties.Selected, isSelected)
                    set(SemanticsProperties.Focused, isSelected)
                    set(SemanticsProperties.Role, Role.Switch)
                    set(SemanticsProperties.Text, listOf(AnnotatedString(operation.label)))
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(choiceSize)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Brush.radialGradient(
                            listOf(Color.Black, Color.LightGray),
                            radius = 170f
                        )
                        else Brush.radialGradient(listOf(Color.White, Color.Gray), radius = 70f)
                    )
            ) {
                Text(
                    operation.label,
                    color = if (isSelected) Color.LightGray else Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
