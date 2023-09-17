package com.example.nftproject.makerfeatures.makeNft

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentMakenftBinding
import com.example.nftproject.makerfeatures.mhome.MhomeFragment
import com.example.nftproject.model.NftMakeRequest
import com.example.nftproject.model.NftcountRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.unity.mynativeapp.config.DialogFragment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MakenftFragment: DialogFragment<FragmentMakenftBinding>(FragmentMakenftBinding::bind, com.example.nftproject.R.layout.fragment_makenft)  {

    private val viewModel by viewModels<MakeViewModel>()
    private var posterFile: File? = null
    private var normalFile: File? = null
    private var rareFile: File? = null
    private var legendFile: File? = null
    private var genreType: String? = null
    private var ageType: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortfilter()
        setUiEvent()
        setCalender()
        setImg()
        subscribeUI()
        hideBottomNavigation(true)
    }
    private fun sortfilter(){
        // 게시물 필터 설정
        binding.genreBtn.setOnClickListener {
            val dialog = PostSortDialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                if(dialog.resultCode == 1){
                    var btnStr = ""
                    btnStr =  btnStr + dialog.checkedCateText
                    binding.genreText.text = Editable.Factory.getInstance().newEditable(btnStr)
                    genreType = MyApplication.genrePartHashMap[dialog.checkedCateText]


                    Log.d("장르",genreType.toString())
                }
            }
        }

        binding.ageBtn.setOnClickListener {
            val dialog = PostageSortDialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                if(dialog.resultCode == 1){
                    var btnStr = ""
                    btnStr =  btnStr + dialog.checkedCateText
                    binding.ageText.text = Editable.Factory.getInstance().newEditable(btnStr)
                    ageType = MyApplication.agePartHashMap[dialog.checkedCateText]

                }
            }
        }
    }

    private fun setCalender(){
        binding.setCalender.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val formattedDate = String.format("%04d-%02d-%02d ", i, i2 + 1, i3)
                binding.opendayText.setText(formattedDate)
            }

            val picker = context?.let { it1 -> DatePickerDialog(it1, listener, year, month, dayOfMonth) }

            if (picker != null) {
                picker.show()
            }
        }
        binding.sellCalender.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val formattedDate = String.format("%04d-%02d-%02d", i, i2 + 1, i3)
                Log.d("날짜",formattedDate)
                binding.saleStartTimeText.setText(formattedDate)
            }

            val picker = context?.let { it1 -> DatePickerDialog(it1, listener, year, month, dayOfMonth) }

            if (picker != null) {
                picker.show()
            }
        }
        binding.sellendCalender.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val formattedDate = String.format("%04d-%02d-%02d", i, i2 + 1, i3)
                binding.saleEndTimeText.setText(formattedDate)
            }

            val picker = context?.let { it1 -> DatePickerDialog(it1, listener, year, month, dayOfMonth) }

            if (picker != null) {
                picker.show()
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
            val genre = genreType
            val age = ageType
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
            Log.d("판매날짜",selldate)
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
                saleStartDate = selldate.takeIf { it !=" " },
                saleEndDate  = selleddate.takeIf { it !=" " },
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
    override fun onDestroyView() {
        super.onDestroyView()
        hideBottomNavigation(false)
    }
    fun hideBottomNavigation(bool: Boolean) {
        val bottomNavigation = activity!!.findViewById<BottomNavigationView>(R.id.nav_dir_bar)
        if (bool == true) bottomNavigation.visibility = View.GONE else bottomNavigation.visibility =
            View.VISIBLE
    }

    private fun subscribeUI() {

        viewModel.logout.observe(this) {
            if (it) logout()
        }

        viewModel.toastMessage.observe(this) {
            showCustomToast(it)
        }

        // 게시글 조회 성공
        viewModel.postWriteSuccess.observe(this) { data ->
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mhomeFrame, MhomeFragment())
                transaction.addToBackStack(null)
                transaction.commit()

        }
    }


}
