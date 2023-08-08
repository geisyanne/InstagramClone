package co.geisyanne.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.base.DependencyInjector
import co.geisyanne.instagram.common.util.TxtWatcher
import co.geisyanne.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.geisyanne.instagram.register.RegisterNamePassword
import co.geisyanne.instagram.register.presentation.RegisterNamePasswordPresenter

class RegisterNamePasswordFragment : Fragment(
    R.layout.fragment_register_name_password
), RegisterNamePassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterNamePassword.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerRepository()
        presenter = RegisterNamePasswordPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL)
            ?: throw java.lang.IllegalArgumentException("email not found")// BUSCAR EMAIL DO FRAG ANTERIOR

        binding?.let {
            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
                registerNameBtnNext.setOnClickListener {
                    presenter.createAccount(
                        email,
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditPasswordConfirm.text.toString()
                    )
                }
                registerEditName.apply {
                    addTextChangedListener(watcher)
                    addTextChangedListener(TxtWatcher {
                        displayNameFailure(null)
                    })
                }
                registerEditPassword.apply {
                    addTextChangedListener(watcher)
                    addTextChangedListener(TxtWatcher {
                        displayPasswordFailure(null)
                    })
                }
                registerEditPasswordConfirm.apply {
                    addTextChangedListener(watcher)
                    addTextChangedListener(TxtWatcher {
                        displayPasswordFailure(null)
                    })
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    private val watcher = TxtWatcher {
        binding?.registerNameBtnNext?.isEnabled =
            binding?.registerEditName?.text.toString().isNotEmpty()
                    && binding?.registerEditPassword?.text.toString().isNotEmpty()
                    && binding?.registerEditPasswordConfirm?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNameBtnNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateSuccess(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }

    companion object {  // CHAVE PARA BUSCAR DE VOLTA O EMAIL DA FRAG ANTERIOR
        const val KEY_EMAIL = "key_email"
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

}