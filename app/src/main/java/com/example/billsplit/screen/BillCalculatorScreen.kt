package com.example.billsplit.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.billsplit.component.AmountDisplayCard
import com.example.billsplit.component.AmountSpent
import com.example.billsplit.component.HappyFace
import com.example.billsplit.component.PersonSelect
import com.example.billsplit.component.TipSelector
import com.example.billsplit.utility.Dimens
import java.text.NumberFormat
import java.util.Locale


@Composable
fun BillCalculatorScreen(
    modifier: Modifier
) {
    val splitedAmount = remember {
        mutableStateOf(0f)
    }

    val amountEntered = remember {
        mutableStateOf("")
    }

    val numberOfPerson = remember {
        mutableStateOf(1)
    }

    val tipPercentage = remember {
        mutableStateOf(0f)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(horizontal = Dimens.padding_10, vertical = Dimens.padding_16)
    ) {

        AmountDisplayCard(
            modifier = Modifier.padding(Dimens.padding_6),
            amount = formatRupee(splitedAmount.value)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.padding_16)
                .border(
                    width = Dimens.padding_1,
                    color = Color(0xFFEBB1F5),
                    shape = AbsoluteRoundedCornerShape(Dimens.padding_8)
                ).animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AmountSpent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding_10),
                enabled = true,
                readOnly = false,
                amount = amountEntered.value,
                onValueChanged = {
                    amountEntered.value = it
                },
                onAmountFilled = {
                    val splitedAmountCalc = calculateSplitedAmount(enteredAmount = amountEntered.value, totalPerson = numberOfPerson.value, tipPercentage= tipPercentage.value)
                    if(splitedAmountCalc!=null){
                        splitedAmount.value = splitedAmountCalc
                    }
                }
            )

            PersonSelect(modifier = Modifier.padding(Dimens.padding_10), noPerson = numberOfPerson.value,
                onMinusClick = {
                    if(numberOfPerson.value>1){
                        numberOfPerson.value-=1
                        val splitedAmountCalc = calculateSplitedAmount(enteredAmount = amountEntered.value, totalPerson = numberOfPerson.value, tipPercentage= tipPercentage.value)
                        if(splitedAmountCalc!=null){
                            splitedAmount.value = splitedAmountCalc
                        }
                    }
                },
                onPlusClick = {
                    numberOfPerson.value+=1
                    val splitedAmountCalc = calculateSplitedAmount(enteredAmount = amountEntered.value, totalPerson = numberOfPerson.value, tipPercentage= tipPercentage.value)
                    if(splitedAmountCalc!=null){
                        splitedAmount.value = splitedAmountCalc
                    }
                })

            TipSelector(
                modifier = Modifier.padding(horizontal = Dimens.padding_2, vertical = Dimens.padding_10),
                range = 100f,
                selectedPerc = tipPercentage.value,
                onValueChange = {
                    tipPercentage.value = it
                },
                onValueChangeFinished = {
                    val splitedAmountCalc = calculateSplitedAmount(enteredAmount = amountEntered.value, totalPerson = numberOfPerson.value, tipPercentage= tipPercentage.value)
                    if(splitedAmountCalc!=null){
                        splitedAmount.value = splitedAmountCalc
                    }
                })
            if(tipPercentage.value>0.5){
                HappyFace(alpha = 0.1f*tipPercentage.value)
            }
        }

    }
}

fun formatRupee(amount: Float): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    return formatter.format(amount)
}

fun checkValidAmount(amount: String?): Boolean {
    if (amount.isNullOrEmpty()) {
        return false
    }
    if (amount.toFloatOrNull() != null) {
        return true
    }
    return false
}

fun calculateSplitedAmount(enteredAmount:String?,totalPerson:Int=1,tipPercentage:Float=0f):Float?{
    if(checkValidAmount(enteredAmount)){
         enteredAmount?.toFloatOrNull()?.let {
            val totalPerPerson = (it + (it*tipPercentage)/100 )/totalPerson
            return totalPerPerson
        }
    }
    return null
}


@Preview(showBackground = true)
@Composable
private fun BillCalculatorScreenPrev() {
    BillCalculatorScreen(modifier = Modifier)
}