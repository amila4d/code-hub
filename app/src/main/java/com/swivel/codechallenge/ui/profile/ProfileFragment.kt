package com.swivel.codechallenge.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.swivel.codechallenge.R
import com.swivel.codechallenge.ui.main.PageViewModel


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var etUsername: TextInputEditText? = null
    private var tilUserName: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        tilUserName = root.findViewById(R.id.textInputLayoutUsername)
        etUsername = root.findViewById(R.id.etUsername)
        val tvUsername : TextView = root.findViewById(R.id.tvUsername)
        val btnSave: MaterialButton = root.findViewById(R.id.btnSave)

        val sharedPref = context!!.getSharedPreferences(
            getString(R.string.shared_preference_file), Context.MODE_PRIVATE)
        val username = sharedPref.getString(getString(R.string.key_username), "N/A")
        tvUsername.text = username
        btnSave.setOnClickListener {
            if (submitForm()) {
                val username = etUsername!!.text!!.trim().toString()
                val sharedPref = context!!.getSharedPreferences(
                    getString(R.string.shared_preference_file), Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPref.edit()
                editor.putString(getString(R.string.key_username), username)
                editor.apply()
                editor.commit()
                //When button click then loading username
                tvUsername.text = sharedPref.getString(getString(R.string.key_username), "N/A")
            }
        }


        return root
    }

    private fun submitForm(): Boolean {
        if (!validateUserName()) {
            return false
        }
        return true
    }

    private fun validateUserName(): Boolean {
        if (etUsername!!.text.toString().trim().isEmpty() || etUsername!!.text!!.isBlank()) {
            tilUserName!!.error = getString(R.string.please_type_username)
            return false
        }  else {
            tilUserName!!.isErrorEnabled = false
        }
        return true
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ProfileFragment {
            return ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}