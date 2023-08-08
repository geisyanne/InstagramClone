package co.geisyanne.instagram.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    // FUNÇÃO PADRÃO
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    var view: View? = currentFocus
    if (view == null) {
        view = View(this) // VIEW TEMPORÁRIA, SIMULANDO FOCUS
    }

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.animationEnd(callback: () -> Unit): AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            callback.invoke()
        }
    }
}

// ID DO FRAME LAYOUT ONDE  TERÁ TROCA DINÂMICA DE FRAGMENTS
fun AppCompatActivity.replaceFragment(@IdRes idContainer: Int, fragment: Fragment ) { // APPCOMPAR PRA TER REF DO SUPPORTFRAGMENT
    if (supportFragmentManager.findFragmentById(idContainer) == null) { // SE, NÃO ADC NGM >>> ADC
        supportFragmentManager.beginTransaction().apply {  // GERENCIADOR DE FRAGMENT
            add(idContainer, fragment, fragment.javaClass.simpleName)  // CONECTAR CONTAINER LAYOUT + FRAGMENT
            commit()  // EFETIVAR MODIFICAÇÕES
        }
    } else { // SE NÃO, JÁ TEM FRAG NA PILHA  >>> TROCAR
        supportFragmentManager.beginTransaction().apply {  // GERENCIADOR DE FRAGMENT
            replace(idContainer, fragment, fragment.javaClass.simpleName)  // CONECTAR CONTAINER LAYOUT + FRAGMENT
            addToBackStack(null)  // EMPILHAR FRAG, VOLTANDO PAARA ANTERIOR
            commit()  // EFETIVAR MODIFICAÇÕES
        }
    }
}