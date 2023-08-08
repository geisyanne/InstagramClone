package co.geisyanne.instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import co.geisyanne.instagram.R
import co.geisyanne.instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment : Fragment(R.layout.fragment_image_cropper) {

    private var binding: FragmentImageCropperBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentImageCropperBinding.bind(view)

        val uri = arguments?.getParcelable<Uri>(KEY_URI) // CAMINHO DA FOTO ORIGINAL

        binding?.let {
            with(it) {
                cropperContainer.setAspectRatio(1, 1) // PROPORÇÃO QUADRADADA
                cropperContainer.setFixedAspectRatio(true)  // PROPORÇÃO FIXA
                cropperContainer.setImageUriAsync(uri)  // INFLAR URI

                cropperBtnCancel.setOnClickListener {
                    parentFragmentManager.popBackStack()  // FORÇAR BTN DE VOLTAR NATIVO
                }

                cropperBtnSave.setOnClickListener {
                    val dir =
                        context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  // DIRETÓRIO NATIVO DE FOTOS
                    if (dir != null) {
                        // SALVAR NOVO ARQ (LOCAL, NOME: HORA+FORMARTO)
                        val uriToSaved = Uri.fromFile(
                            File(
                                dir.path,
                                System.currentTimeMillis().toString() + ".jpeg"
                            )
                        )
                        cropperContainer.saveCroppedImageAsync(uriToSaved)  // SALVAR
                    }
                }

                // LISTENER PARA TODA VEZ Q A IMAGEM FOR CORTADA, O RESULT TRAZ O CAMINHO ONDE FOI SALVA
                cropperContainer.setOnCropImageCompleteListener { view, result ->
                    // Log.i("Teste", "nova imagem ${result.uri}")
                    // CHAVE DE REQUISIÇÃO PARA NOVA URI
                    setFragmentResult("cropKey", bundleOf(KEY_URI to result.uri))
                    parentFragmentManager.popBackStack()  // VOLTAR PARA TELA DE REGISTRAR FOTO
                }
            }
        }

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_URI = "key_uri"
    }
}