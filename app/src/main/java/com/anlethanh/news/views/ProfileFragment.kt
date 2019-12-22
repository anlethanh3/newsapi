package com.anlethanh.news.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anlethanh.news.R
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.databinding.ProfileBinding
import com.anlethanh.news.databinding.RegisterBinding
import com.anlethanh.news.models.Profile
import kotlinx.android.synthetic.main.profile.view.*
import kotlinx.android.synthetic.main.register.view.*

class ProfileFragment(val helper: Helper) : Fragment(), ICallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context?.run {
            helper.loadData(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback()
    }

    override fun callback() {
        view.apply {
            activity?.run {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment =
                    if (helper.profile != null) InfoFragment(
                        helper,
                        this@ProfileFragment
                    ) else RegisterFragment(
                        helper,
                        this@ProfileFragment
                    )
                transaction.replace(R.id.pl_frame, fragment)
                transaction.commit()
            }
        }
    }
}

interface ICallback {
    public fun callback()
}

class InfoFragment(val helper: Helper, val callback: ICallback) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile, container, false)
        binding.viewmodel = helper.profile
        return binding.root
    }

    lateinit var binding: ProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            this.pr_sign_out.setOnClickListener {
                helper.removeData(context)
                callback.callback()
            }
        }

    }
}

class RegisterFragment(val helper: Helper, val callback: ICallback) : Fragment() {
    var profile = Profile()
    lateinit var binding: RegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register, container, false)
        binding.viewmodel = profile
        return binding.root
    }

    fun isValid(): Boolean {
        return isValidUsername() && isValidEmail() && isValidPassword() && isValidFullname()
    }

    fun isValidUsername(): Boolean {
        var result = false
        binding.usernameText?.text?.toString()?.run {
            if (this.length < 6) {
                binding.usernameLayout.error = "Username is minimum 6 characters"
            } else {
                binding.usernameLayout.error = null
                result = true
            }
        } ?: kotlin.run {
            binding.usernameLayout.error = "Username is require"
        }
        return result
    }

    fun isValidFullname(): Boolean {
        var result = false
        binding.fullnameText?.text?.toString()?.run {
            if (this.length < 6) {
                binding.fullnameLayout.error = "Fullname is minimum 6 characters"
            } else {
                binding.fullnameLayout.error = null
                result = true
            }
        } ?: kotlin.run {
            binding.fullnameLayout.error = "Fullname is require"
        }
        return result
    }

    fun isValidEmail(): Boolean {
        var result = false
        binding.emailText?.text?.toString()?.run {
            if (!helper.validEmail(this)) {
                binding.emailLayout.error = "Email not valid"
            } else {
                binding.emailLayout.error = null
                result = true
            }
        } ?: kotlin.run {
            binding.emailLayout.error = "Email is require"
        }
        return result
    }

    fun isValidPassword(): Boolean {
        var result = false
        binding.passwordText?.text?.toString()?.run {
            if (this.length < 6) {
                binding.passwordLayout.error = "Password is minimum 6 characters"
            } else {
                binding.passwordLayout.error = null
                result = true
            }
        } ?: kotlin.run {
            binding.passwordLayout.error = "Password is require"
        }
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            this.pr_register.setOnClickListener {
                if (isValid()) {
                    helper.saveData(context, binding.viewmodel!!)
                    callback.callback()
                }
            }
            binding.usernameText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isValidUsername()
                }
            })

            binding.fullnameText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isValidFullname()
                }
            })

            binding.passwordText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isValidPassword()
                }
            })

            binding.emailText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isValidEmail()
                }
            })
        }

    }
}