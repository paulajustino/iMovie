package com.example.imovie.presentation.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.imovie.databinding.FragmentLoginBinding
import com.example.imovie.presentation.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelProviderFactory }

    private lateinit var bindingLoginFragment: FragmentLoginBinding
}