package com.example.nftproject.makerfeatures.makeNft

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nftproject.databinding.FragmentMakenftBinding
import com.example.nftproject.model.NftMakeRequest
import com.example.nftproject.model.NftcountRequest
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*

class MakenftFragment : Fragment() {
    private lateinit var binding: FragmentMakenftBinding
    private val viewModel by viewModels<MakeViewModel>()
    private var posterFile: File? = null
    private var normalFile: File? = null
    private var rareFile: File? = null
    private var legendFile: File? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakenftBinding.inflate(inflater, container, false)

        setUiEvent()
        setCalender()
        setImg()
        return binding.root
    }


    private fun setCalender(){
        binding.setCalender.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                binding.opendayText.setText("${i}-${i2 + 1}-${i3}")
            }
            var picker = context?.let { it1 -> DatePickerDialog(it1, listener, year, month, dayOfMonth) }

            if (picker != null) {
                picker.show()
            }
        }
        binding.sellCalender.setOnClickListener {
            var sellcalendar = Calendar.getInstance()
            var sellyear = sellcalendar.get(Calendar.YEAR)
            var sellmonth = sellcalendar.get(Calendar.MONTH)
            var selldayOfMonth = sellcalendar.get(Calendar.DAY_OF_MONTH)

            var selllistener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                binding.saleStartTimeText.setText("${i}-${i2 + 1}-${i3}")
            }
            var sellpicker = context?.let { it1 -> DatePickerDialog(it1, selllistener, sellyear, sellmonth, selldayOfMonth) }

            if (sellpicker != null) {
                sellpicker.show()
            }
        }
        binding.sellendCalender.setOnClickListener {
            var secalendar = Calendar.getInstance()
            var seyear = secalendar.get(Calendar.YEAR)
            var semonth = secalendar.get(Calendar.MONTH)
            var sedayOfMonth = secalendar.get(Calendar.DAY_OF_MONTH)

            var selistener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                binding.saleEndTimeText.setText("${i}-${i2 + 1}-${i3}")
            }
            var sepicker = context?.let { it1 -> DatePickerDialog(it1, selistener, seyear, semonth, sedayOfMonth) }

            if (sepicker != null) {
                sepicker.show()
            }
        }
    }
    private val pactivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.posterImg)
            posterFile = uri?.let { it1 -> uriToFile(it1, "poster.jpg") }
        }
    }
    private val nactivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.normalImg)
            normalFile = uri?.let { it1 -> uriToFile(it1, "normal.jpg") }
        }
    }
    private val ractivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.rareImg)
            rareFile = uri?.let { it1 -> uriToFile(it1, "rare.jpg") }
        }
    }
    private val lactivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.legendImg)
            legendFile = uri?.let { it1 -> uriToFile(it1, "legend.jpg") }
        }
    }
    private fun setImg(){
        binding.posterImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            pactivityResult.launch(intent)
        }
        binding.normalImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            nactivityResult.launch(intent)
        }
        binding.rareImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            ractivityResult.launch(intent)
        }
        binding.legendImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            lactivityResult.launch(intent)
        }
    }
    private fun uriToFile(uri: Uri, filename: String): File {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val file = File(requireContext().cacheDir, filename)
        file.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        return file
    }
    private fun setUiEvent() {
        binding.publishBtn.setOnClickListener {
            val title = binding.titleText.text.toString()
            val genre = binding.genreText.text.toString()
            val age = binding.ageText.text.toString()
            val openday = binding.opendayText.text.toString()
            val director = binding.direText.text.toString()
            val actor = binding.actorText.text.toString()
            val actorArray = actor.split(",").map { it.trim() }.toTypedArray()
            val movietime: Int = try {
                binding.movietimeText.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0  // Default value if not a valid integer.
            }
            val normalprice: Int = try {
                binding.normalPrice.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0  // Default value if not a valid integer.
            }
            val selldate = binding.saleStartTimeText.text.toString()
            val selleddate = binding.saleStartTimeText.text.toString()
            val overview = binding.overviewText.text.toString()
            val show = true
            val jsonRequest = NftMakeRequest(
                movieTitle = title.takeIf { it !=" " },
                movieGenre = genre.takeIf { it !=" " },
                filmRating = age.takeIf { it !=" " },
                releaseDate = openday.takeIf { it !=" " },
                director = director.takeIf { it !=" " },
                actors = actorArray.takeIf { it.isNotEmpty() },
                runningTime = movietime.takeIf { it != null },
                normalNFTPrice = normalprice.takeIf { it != null },
                saleStartTime = selldate.takeIf { it !=" " },
                saleEndTime = selleddate.takeIf { it !=" " },
                overView = overview.takeIf { it !=" " },
                show = show
            )
            val gson = GsonBuilder().serializeNulls().create()
            val requestBodyString = gson.toJson(jsonRequest).toString()
            val publishnft1 = requestBodyString.toRequestBody("application/json".toMediaType())

            val normalc: Int = try {
                binding.normalQuantity.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0  // Default value if not a valid integer.
            }
            val rarec: Int = try {
                binding.rareQuantity.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0  // Default value if not a valid integer.
            }
            val legendc: Int = try {
                binding.legendQuantity.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0  // Default value if not a valid integer.
            }

            val jsonRequest2 = NftcountRequest(
                normalCount = normalc.takeIf { it != null },
                rareCount = rarec.takeIf { it != null },
                legendCount = legendc.takeIf { it != null }
            )
            val gson2 = GsonBuilder().serializeNulls().create()
            val requestBodyString2 = gson2.toJson(jsonRequest2).toString()
            val publishnft2 = requestBodyString2.toRequestBody("application/json".toMediaType())

            val posterRequestBody = posterFile?.let { it1 -> RequestBody.create("image/jpeg".toMediaTypeOrNull(), it1) }
            val posterPart = posterRequestBody?.let { it1 -> MultipartBody.Part.createFormData("poster", posterFile?.name, it1) }
            val normalRequestBody = normalFile?.let { it1 -> RequestBody.create("image/jpeg".toMediaTypeOrNull(), it1) }
            val normalPart = normalRequestBody?.let { it1 -> MultipartBody.Part.createFormData("normal", normalFile?.name, it1) }
            val rareRequestBody = rareFile?.let { it1 -> RequestBody.create("image/jpeg".toMediaTypeOrNull(), it1) }
            val rarePart = rareRequestBody?.let { it1 -> MultipartBody.Part.createFormData("rare", rareFile?.name, it1) }
            val legendRequestBody = legendFile?.let { it1 -> RequestBody.create("image/jpeg".toMediaTypeOrNull(), it1) }
            val legendPart = legendRequestBody?.let { it1 -> MultipartBody.Part.createFormData("legend", legendFile?.name, it1) }
            viewModel.publishnft(publishnft1,publishnft2,posterPart,normalPart,rarePart,legendPart)
            Log.d("json파일",publishnft1.toString())
            Log.d("json파일2",publishnft2.toString())
        }
    }
    private fun createImagePart(name: String, file : File): MultipartBody.Part {
        val requestBody =
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(name, file.name, requestBody)
    }
}
