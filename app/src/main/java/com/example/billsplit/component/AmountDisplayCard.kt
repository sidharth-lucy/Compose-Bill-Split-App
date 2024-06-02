package com.example.billsplit.component

import android.app.LocaleManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsplit.R
import com.example.billsplit.utility.Dimens


@Composable
fun AmountDisplayCard(
    modifier: Modifier,
    amount: String
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.padding_130)
            .clip(shape = RoundedCornerShape(Dimens.padding_25))
            .background(color = Color(0xFFBFA5F7)),
        contentAlignment = Alignment.Center

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = amount,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            Text(
                text = "Per Person",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(800),
                modifier = Modifier
            )

        }

    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AmountSpent(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    labelText: String = "Amount Spent",
    amount: String,
    onValueChanged: (amount:String) -> Unit,
    onAmountFilled:()->Unit,
) {
    val localFocusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = amount,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = labelText,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFBFA5F7)
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(Dimens.padding_30),
                painter = painterResource(id = R.drawable.ic_rupee),
                contentDescription = ""
            )
        },
        singleLine = true,
        onValueChange = {
            onValueChanged(it)
        },
        keyboardActions = KeyboardActions(onNext = {
            KeyboardActions.Default
            localFocusManager.clearFocus()
            onAmountFilled()
        }),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}


@Composable
fun PersonSelect(modifier: Modifier = Modifier,noPerson:Int=1, onMinusClick: () -> Unit, onPlusClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Total person:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.padding_16)
        ) {
            RoundedButton(
                icons = painterResource(id = R.drawable.ic_minus_sign),
                onClick = { onMinusClick() })
            Text(text = noPerson.toString(), style = MaterialTheme.typography.bodyLarge)
            RoundedButton(
                icons = painterResource(id = R.drawable.ic_plus_sign),
                onClick = { onPlusClick() })
        }
    }
}

@Composable
fun RoundedButton(icons: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(Dimens.padding_40)
            .background(color = Color(0xFFE7F7D0))
            .border(width = Dimens.padding_0_5, color = Color(0xFF99F514), shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(modifier = Modifier.size(Dimens.padding_25), painter = icons, contentDescription = "")
    }
}


@Composable
fun TipSelector(
    modifier: Modifier,
    range: Float = 100f,
    selectedPerc:Float=0f,
    onValueChange: (data: Float) -> Unit,
    onValueChangeFinished: () -> Unit
) {
    Column(modifier = modifier) {
        Text(modifier = Modifier.padding(horizontal = Dimens.padding_8),
            text = if(selectedPerc<=0) "Happy Tip %" else "Happy Tip ${"%.2f".format(selectedPerc)}%" ,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFFCD8CF1)
        )
        Slider(
            value = selectedPerc,
            onValueChange = {
                onValueChange(it)
            },
            onValueChangeFinished = {
                onValueChangeFinished()
            },
            valueRange = 0f..range,
//            steps = if (range >= 50) range / 10 else 5
        )
    }

}

@Composable
fun HappyFace(alpha:Float=0f) {
    Box(modifier = Modifier.size(Dimens.padding_70).alpha(alpha)) {
        Image(painter = painterResource(id = R.drawable.happy_love_emoji), contentDescription ="love" )
    }
}

@Preview(showBackground = true)
@Composable
private fun HappyFacePrev() {
    HappyFace(alpha = 0.1f)
}

@Preview(showBackground = true)
@Composable
private fun TipSelectorPrev() {
    TipSelector(modifier = Modifier, range = 100f, onValueChangeFinished = {}, onValueChange = {})
}

@Preview(showBackground = true)
@Composable
private fun PersonSelectPrev() {
    PersonSelect(onMinusClick = {}, onPlusClick = {})
}

@Preview(showBackground = true)
@Composable
private fun RoundedButtonPrev() {
    RoundedButton(icons = painterResource(id = R.drawable.ic_minus_sign), {})
}


@Preview(showBackground = true)
@Composable
private fun AmountSpentPrev() {
    AmountSpent(
        enabled = true,
        readOnly = false,
        amount = "1000",
        onValueChanged = {},
        onAmountFilled = {}
    )
}


@Preview(showBackground = true)
@Composable
private fun AmountDisplayCardPrev() {
    AmountDisplayCard(modifier = Modifier, amount = "500")
}