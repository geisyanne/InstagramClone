package co.geisyanne.instagram.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.base.DependencyInjector
import co.geisyanne.instagram.common.util.TxtWatcher
import co.geisyanne.instagram.databinding.FragmentRegisterEmailBinding
import co.geisyanne.instagram.register.RegisterEmail
import co.geisyanne.instagram.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email),
    RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null

    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        val repository = DependencyInjector.registerRepository()
        presenter = RegisterEmailPresenter(this, repository)

        binding?.let {
            with(it) {
                when (resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        registerImgPhoto.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                    }
                }

                registerTxtLogin.setOnClickListener {
                    activity?.finish()  // PARA VOLTAR A TELA DE LOGIN
                }

                registerBtnNext.setOnClickListener {
                    presenter.createEmail(registerEditEmail.text.toString())
                }

                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)  // AO ALTERAR EDIT, SAI MSG DE ERRO
                })
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) { //  SE A CLASSE Q IMPLEMENTA FOR 'FragmentAttachListener'
            fragmentAttachListener = context  // GUARDAR REF DA ACT
        }
    }

    private val watcher = TxtWatcher {
        binding?.registerBtnNext?.isEnabled =
            binding?.registerEditEmail?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
    }

    override fun onDestroy() {
        fragmentAttachListener = null
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}