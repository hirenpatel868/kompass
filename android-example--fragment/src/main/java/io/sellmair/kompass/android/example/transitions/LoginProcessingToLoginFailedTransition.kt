package io.sellmair.kompass.android.example.transitions

import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import io.sellmair.kompass.android.example.fragment.LoginFailedFragment
import io.sellmair.kompass.android.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.core.Route


/**
 * Created by sebastiansellmair on 07.02.18.
 */
class LoginProcessingToLoginFailedTransition :
    GenericFragmentTransition<LoginProcessingFragment, Route, LoginFailedFragment, Route> {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: LoginProcessingFragment, exitRoute: Route,
        enterFragment: LoginFailedFragment, enterRoute: Route
    ) {
        exitFragment.exitTransition = Slide(Gravity.TOP)
        enterFragment.enterTransition = Slide(Gravity.BOTTOM)
    }
}
