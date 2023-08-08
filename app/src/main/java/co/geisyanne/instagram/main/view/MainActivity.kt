package co.geisyanne.instagram.main.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.extension.replaceFragment
import co.geisyanne.instagram.databinding.ActivityMainBinding
import co.geisyanne.instagram.home.view.HomeFragment
import co.geisyanne.instagram.main.LogoutListener
import co.geisyanne.instagram.post.view.AddFragment
import co.geisyanne.instagram.profile.view.ProfileFragment
import co.geisyanne.instagram.search.view.SearchFragment
import co.geisyanne.instagram.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    AddFragment.AddListener, SearchFragment.SearchListener, LogoutListener,
    ProfileFragment.FollowListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: ProfileFragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val currentNightMode =
            resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                    binding.mainImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    window.insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
                }
            }
        } else {
            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    binding.mainImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
        }

        setSupportActionBar(binding.mainToolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // FRAGMENT FIXED

        //INSTACIAR FRAGMENTS
        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this) // IMPLEMENTAÇÃO DO LISTENER
        binding.mainBottomNav.selectedItemId =
            R.id.menu_bottom_home  // PARA SELECIONAR JÁ O 1º FRAG

    }

    //CONFIG TOOLBAR PROGRAMATICAMENTE
    private fun setScrollToolbarEnabled(enabled: Boolean) {
        val params = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        var coordinatorParams = binding.mainAppbar.layoutParams as CoordinatorLayout.LayoutParams

        if (enabled) {
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        } else { // RESETAR COMPORTAMENTOS
            params.scrollFlags = 0
            coordinatorParams.behavior = null
        }
        binding.mainAppbar.layoutParams = coordinatorParams
    }

    override fun goToProfile(uuid: String) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.KEY_USER_ID, uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.container_main_fragment,
                fragment,
                fragment.javaClass.simpleName + "detail"
            )  // PRA SABER Q É DIFERENTE
            addToBackStack(null)
            commit()
        }
    }

    override fun followUpdated() {  // LIMPAR CACHE DO PERFIL - REALOAD
        homeFragment.presenter.clear()

        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {  // SE ProfileFragment NÃO TIVER SIDO CARREGADO
            profileFragment.presenter.clear()
        }
    }

    override fun logout() {
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {  // SE ProfileFragment NÃO TIVER SIDO CARREGADO
            profileFragment.presenter.clear()
        }

        homeFragment.presenter.clear()

        homeFragment.presenter.logout()

        val intent = Intent(baseContext, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    // TROCAR FRAGMENTS COM BASE NO EVENTO DE CLICK DA BOTTOM NAVIGATION
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnabled = false

        // FRAGMENTS FIXED
        when (item.itemId) {  // TROCAR FRAG BASEADO NO ID
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment) return false // PARA NÃO ABRIR +1X MSM FRAG
                currentFragment = homeFragment
            }
            R.id.menu_bottom_search -> {
                if (currentFragment == searchFragment) return false
                currentFragment = searchFragment
                scrollToolbarEnabled = false
            }
            R.id.menu_bottom_add -> {
                if (currentFragment == addFragment) return false
                currentFragment = addFragment
                scrollToolbarEnabled = false // DESATIVAR ROLAGEM DA TOOLBAR
            }
            R.id.menu_bottom_profile -> {
                if (currentFragment == profileFragment) return false
                currentFragment = profileFragment
                scrollToolbarEnabled = true
            }
        }

        setScrollToolbarEnabled(scrollToolbarEnabled)

        currentFragment?.let {  // INFLAR FRAG
            replaceFragment(R.id.container_main_fragment, it)
        }

        return true  // BTN SELECIONADO
    }

    override fun onPostCreated() {
        homeFragment.presenter.clear()

        // SE O USUÁRIO AINDA NÃO CARREGOU A TELA DE PERFIL, O PRESENTER ESTARÁ NULL
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {
            profileFragment.presenter.clear()
        }

        binding.mainBottomNav.selectedItemId =
            R.id.menu_bottom_home // MOSTRAR NOVAMENTE A TELA DO FEED
    }


}