package com.example.mastermind.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import com.example.mastermind.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.mastermind.data.ColourOptions
import kotlinx.coroutines.flow.update
import java.util.Arrays

class MasterMindViewModel : ViewModel(){
    private val TAG = "MasterMindViewModel"

    // game UI state

    // backing property
    private val _uiState = MutableStateFlow(MasterMindUiState()) // actual one
    val uiState: StateFlow<MasterMindUiState> = _uiState.asStateFlow() // non private one that can be changed from outside the view model


    private val allColourOptions = arrayOf(ColourOptions.YELLOW, ColourOptions.GREY, ColourOptions.ORANGE, ColourOptions.GREEN, ColourOptions.RED, ColourOptions.BLUE)

    var randomSequence: Array<ColourOptions> = generateRandSequence()

    var currentPos by mutableIntStateOf(0) // should use this one in actual just cba making it work with this right now

    var userGuess by mutableStateOf(arrayOf(ColourOptions.WHITE, ColourOptions.WHITE, ColourOptions.WHITE, ColourOptions.WHITE))

    var gameBoard = Array(8) {Array(4) {R.drawable.white_blob} }
    private val blankGameBoard = Array(8) {Array(4) {R.drawable.white_blob} }
    var feedBackBoard = Array(8) {Array(4) {R.drawable.blankimg} }
    private val blankFeedBackBoard = Array(8) {Array(4) {R.drawable.blankimg} }

    var numOfGuesses by mutableIntStateOf(0)



    var imgRes1 by mutableIntStateOf(R.drawable.white_blob)
    var imgRes2 by mutableIntStateOf(R.drawable.white_blob)
    var imgRes3 by mutableIntStateOf(R.drawable.white_blob)
    var imgRes4 by mutableIntStateOf(R.drawable.white_blob)


    fun updateUserGuess(colour: ColourOptions, pos: Int){
        userGuess[pos] = colour
    }

    fun resetUserGuess() {
        for (j in 0..3) {
            userGuess[j] = ColourOptions.BLANK
        }
        imgRes1 = R.drawable.white_blob
        imgRes2 = R.drawable.white_blob
        imgRes3 = R.drawable.white_blob
        imgRes4 = R.drawable.white_blob
    }

    fun resetGame() {
        resetUserGuess()
        updateNumGuesses(0)
        gameBoard = Array(8) {Array(4) {R.drawable.white_blob} }
        feedBackBoard = Array(8) {Array(4) {R.drawable.blankimg} }
        //updateFeedbackBoard(blankFeedBackBoard)
        randomSequence = generateRandSequence()

        _uiState.value = MasterMindUiState()
        //_uiState.value = GameUiState(currentScrambledWord = pickRandomAndShuffle())

    }
    init { // if this is before these lines above the app will run on start as i think we need the aboe lines
        resetGame()
    }

    fun updateCurrentPos(newCurrentPos: Int) {
        currentPos = newCurrentPos
    }

    fun updateNumGuesses(newNumGuesses: Int) {
        numOfGuesses = newNumGuesses
    }

    fun updateGameBoard(newGameBoard: Array<Array<Int>>) {
        gameBoard = newGameBoard
    }

    fun updateFeedbackBoard(newGameBoard: Array<Array<Int>>) {
        feedBackBoard = newGameBoard
    }



    fun checkUserGuess (
    ): Array<Int> {

        var randToCheck = Array(4){ColourOptions.WHITE}
        Log.d(TAG, "user guess when checking: ${userGuess.contentToString()}")


        // build the random sequece to check the user guess against and change values in
        for (x in 0..3) {
            randToCheck[x] = randomSequence[x]
        }

        var correctInPlace = 0
        var correctWrongPlace = 0

        if (currentPos == 4) { // correct amount of entries
            // move to here
            //var toInc = numOfGuesses
            //toInc++
            if (numOfGuesses < 8) {
                var newGameBoard = gameBoard
                val userGuessForGameBoard = arrayOf(1,1,1,1)
                for (x in 0..3){
                    when(userGuess[x]) {
                        ColourOptions.BLUE -> {userGuessForGameBoard[x] = R.drawable.blue}
                        ColourOptions.GREY -> {userGuessForGameBoard[x] = R.drawable.grey}
                        ColourOptions.GREEN -> {userGuessForGameBoard[x] = R.drawable.green}
                        ColourOptions.RED -> {userGuessForGameBoard[x] = R.drawable.red}
                        ColourOptions.ORANGE -> {userGuessForGameBoard[x] = R.drawable.orange}
                        else -> {userGuessForGameBoard[x] = R.drawable.yellow}
                    }

                }

                newGameBoard[numOfGuesses] = userGuessForGameBoard

                for (j in 0..7) {
                    Log.d(TAG, gameBoard[j].contentToString())
                }
                //Log.d(TAG, newGameBoard[0].toString())
                    //updateNumGuesses(toInc)
                updateNumGuesses(numOfGuesses+1)
            }
            if (numOfGuesses == 8){
                // reached max number of guesses game is over player loses
                _uiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true
                    )
                }
            }
            // end of moved code



            if (userGuess.contentEquals(randToCheck)) { // correct sequence
                //THIS SHOULD END THE GAME
                correctInPlace = 4
                _uiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true,
                        playerWon = true
                    )
                }

            } else { // incorrect sequence
                // check what is correct and where
                for (i in 0..3) {
                    if (userGuess[i] == randToCheck[i]) {// randToCheck was randomSequence
                        correctInPlace++
                        randToCheck[i] = ColourOptions.WHITE
                        userGuess[i] = ColourOptions.BLANK
                    }
                }
                Log.d("BEFORE CHECK", Arrays.toString(userGuess))

                for (y in 0..3) {// this doesnt work need to only check things once
                    if (userGuess[y] in randToCheck){
                        correctWrongPlace++
                        // find the random that was the same and remove it
                        for (x in 0..3){
                            if (userGuess[y] == randToCheck[x]){
                                randToCheck[x] = ColourOptions.WHITE
                            }
                        }
                    }
                    /*

                    if (randToCheck.contains(userGuess[y])) {
                        correctWrongPlace++
                    }
                    */
                }
                Log.d("AFTER CHECK", Arrays.toString(userGuess))


            }
            /*
            var toInc = numOfGuesses
            toInc++
            if (numOfGuesses < 7) {
                var newGameBoard = gameBoard
                val userGuessForGameBoard = arrayOf(1,1,1,1)
                for (x in 0..3){
                    when(userGuess[x]) {
                        ColourOptions.BLUE -> {userGuessForGameBoard[x] = R.drawable.blue}
                        ColourOptions.GREY -> {userGuessForGameBoard[x] = R.drawable.grey}
                        ColourOptions.GREEN -> {userGuessForGameBoard[x] = R.drawable.green}
                        ColourOptions.RED -> {userGuessForGameBoard[x] = R.drawable.red}
                        ColourOptions.ORANGE -> {userGuessForGameBoard[x] = R.drawable.orange}
                        else -> {userGuessForGameBoard[x] = R.drawable.yellow}
                    }

                }

                newGameBoard[numOfGuesses] = userGuessForGameBoard

                for (j in 0..7) {
                    Log.d(TAG, gameBoard[j].contentToString())
                }
                //Log.d(TAG, newGameBoard[0].toString())
                updateNumGuesses(toInc)
            }else {
                // reached max number of guesses game is over player loses
                _uiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true
                    )
                }
            }
            */

        } else {
            Log.d(TAG, "not enough entries")
        }
        Log.d(TAG, "feedbackvals $correctInPlace, $correctWrongPlace")
        return arrayOf(correctInPlace, correctWrongPlace)
    }


    /*
     fun updateFeedbackBlobs() {
        val feedBackVals = checkUserGuess()
        val correctInPlace = feedBackVals[0]
        val correctWrongPlace = feedBackVals[1]

        // update feedback loops, awful code, works but looks horrid FIX/MAKE LOOK NICE
        when (correctInPlace) {
            0 -> {
                when (correctWrongPlace) {
                    1 -> {
                        feedBackBoard[numOfGuesses][0] = R.drawable.black_blob
                    }
                    2 -> {
                        feedBackBoard[numOfGuesses][0] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob

                    }
                    3 -> {
                        feedBackBoard[numOfGuesses][0] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob
                    }
                    4 -> {
                        feedBackBoard[numOfGuesses][0] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][3] = R.drawable.black_blob
                    }
                }
            }
            1 -> {
                feedBackRes1 = R.drawable.white_blob
                when (correctWrongPlace) {
                    1 -> {
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob
                    }
                    2 -> {
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob

                    }
                    3 -> {
                        feedBackBoard[numOfGuesses][1] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][3] = R.drawable.black_blob
                    }
                }
            }
            2 -> {
                feedBackBoard[numOfGuesses][0] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][1] = R.drawable.white_blob

                when (correctWrongPlace) {
                    1 -> {
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob
                    }

                    2 -> {
                        feedBackBoard[numOfGuesses][2] = R.drawable.black_blob
                        feedBackBoard[numOfGuesses][3] = R.drawable.black_blob
                    }
                }
            }


            3 -> {
                feedBackBoard[numOfGuesses][0] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][1] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][2] = R.drawable.white_blob
                if (correctWrongPlace == 1) feedBackBoard[numOfGuesses][3] = R.drawable.black_blob
            }
            4 -> {
                feedBackBoard[numOfGuesses][0] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][1] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][2] = R.drawable.white_blob
                feedBackBoard[numOfGuesses][3] = R.drawable.white_blob

            }
        }

    }
    */

    private fun generateRandSequence(): Array<ColourOptions> {
        val outputSequence = arrayOf(ColourOptions.WHITE,ColourOptions.WHITE,ColourOptions.WHITE,ColourOptions.WHITE)
        for (x in 0..3) {
            val randomNum = (0..5).random()
            outputSequence[x] = allColourOptions[randomNum]
        }
        return outputSequence
    }

    fun onUiColourButtonPressed(colour: ColourOptions) {
        val imgRes = when (colour){
            ColourOptions.BLUE   -> {R.drawable.blue}
            ColourOptions.GREY   -> {R.drawable.grey}
            ColourOptions.GREEN  -> {R.drawable.green}
            ColourOptions.RED    -> {R.drawable.red }
            ColourOptions.ORANGE -> {R.drawable.orange}
            else -> {R.drawable.yellow}
        }
        if (currentPos <= 3) {
            updateUserGuess(
                colour = colour,
                pos = currentPos
            )
            if (currentPos <= 4){
                when(currentPos) {
                    0 -> {
                        //gameBoard[numOfGuesses][currentPos] = imgRes
                        imgRes1 = imgRes
                    }
                    1 -> {
                        //gameBoard[numOfGuesses][currentPos] = imgRes
                        imgRes2 = imgRes
                    }
                    2 -> {
                        //gameBoard[numOfGuesses][currentPos] = imgRes
                        imgRes3 = imgRes
                    }
                    else ->{
                        //gameBoard[numOfGuesses][currentPos] = imgRes
                        imgRes4 = imgRes
                    }
                }
            }

            Log.d("SPACER", "SPACER")
            //Log.d(TAG, gameBoard[numOfGuesses].contentToString())


            val currPosChecker = currentPos
            if (currPosChecker < 5) updateCurrentPos(currPosChecker+1)
            //Log.d(TAG, "After, ${currentPos.toString()}")
        }
    }
}

/*
fun onUiColourButtonPressed(colour: ColourOptions) {
        val imgRes = when (colour){
            ColourOptions.BLUE   -> {R.drawable.blue}
            ColourOptions.GREY   -> {R.drawable.grey}
            ColourOptions.GREEN  -> {R.drawable.green}
            ColourOptions.RED    -> {R.drawable.red }
            ColourOptions.ORANGE -> {R.drawable.orange}
            else -> {R.drawable.yellow}
        }
        if (currentPos <= 4) {
            updateUserGuess(
                colour = colour,
                pos = currentPos-1
            )
            if (currentPos <= 5){
                when(currentPos) {
                    1 -> imgRes1 = imgRes
                    2 -> imgRes2 = imgRes
                    3 -> imgRes3 = imgRes
                    else -> imgRes4 = imgRes
                }
            }
            val currPosChecker = currentPos
            if (currPosChecker < 5) updateCurrentPos(currPosChecker+1)
        }
    }
 */