package com.example.navigationcomponent.extensoes

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.navigationcomponent.R

private val navOptions= NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateComAnimacao(destinationId: Int){
/*    val options = navOptions {
*        anim {
*            enter = R.anim.slide_in_right
*            exit = R.anim.slide_out_left
*            popEnter = R.anim.slide_in_left
*            popExit = R.anim.slide_out_right
*        }
*    }
*
 */
    this.navigate(destinationId,null, navOptions)
}
