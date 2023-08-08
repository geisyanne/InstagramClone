package co.geisyanne.instagram.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.extension.hideKeyboard
import co.geisyanne.instagram.common.extension.replaceFragment
import co.geisyanne.instagram.common.view.CropperImageFragment
import co.geisyanne.instagram.common.view.CropperImageFragment.Companion.KEY_URI
import co.geisyanne.instagram.databinding.ActivityRegisterBinding
import co.geisyanne.instagram.main.view.MainActivity
import co.geisyanne.instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL
import co.geisyanne.instagram.register.view.RegisterWelcomeFragment.Companion.KEY_NAME
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhoto: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterEmailFragment()
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
/*        val args = Bundle()
        args.putString(KEY_EMAIL, email)
        val fragment = RegisterNamePasswordFragment()
        fragment.arguments = args */

        val fragment = RegisterNamePasswordFragment().apply { // PASSAR ARGS PARA OUTRO FRAG
            arguments = Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = RegisterWelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_NAME, name)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToPhotoScreen() {
        val fragment = RegisterPhotoFragment()
        replaceFragment(fragment)
    }

    // INSTRUÇÃO PADRÃO PARA TIRAR FOTO
    private val getCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
            if (saved) { // RETORNA BOOLEAN INDICANDO SE A IMG FOI SALVA OU NÃO NA URI FORNECIDA
                openImageCropper(currentPhoto)  // CHAMAR O CROPPER, PASSANDO URI
            }
        }

    override fun goToCameraScreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {

            val photoFile: File? = try {
                createImageFile()
            } catch (e: IOException) {
                Log.e("RegisterActivity", e.message, e)
                null
            }

            photoFile?.also {
                val photoUri =
                    FileProvider.getUriForFile(this, "co.geisyanne.instagram.fileprovider", it)
                currentPhoto = photoUri  // CAMINHO DA URI ARMAZENADA EM MEMÓRIA

                getCamera.launch(photoUri)  // LANÇAR APP DE CAMERA
            }
        }
    }

    // CRIAR UM ARQUIVO FIXO
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date()) // PARA NOME DO ARQ
        val dir =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES) // LOCAL ONDE SERÁ ARMAZENADO // DISPONÍVEL APENAS PARA ESSEAPP
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", dir) // CRIAR ARQUIVO
    }

    // ABRIR APP DE GALERIA PADRÃO DO CELL
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { openImageCropper(it) }
        }

    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.container_register_fragment, fragment)
        hideKeyboard()
    }

    // CHAMAR O CROPPER, PASSAR OS ARGUMENTOS PARA O FRAGMENT
    private fun openImageCropper(uri: Uri) {
        val fragment = CropperImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }
        replaceFragment(fragment)
    }
}