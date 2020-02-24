package me.sebhernoux.dmii.fragments

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.parcel.Parcelize
import me.sebhernoux.dmii.R
import kotlinx.android.synthetic.main.computation_fragment.*
import me.sebhernoux.dmii.extensions.toDouble
import me.sebhernoux.dmii.viewmodel.ComputeViewModel

class ComputationFragment : Fragment() {
    var nb1: Double = 0.0
    var nb2: Double = 0.0

    val operation: Operation by lazy {
        arguments?.getParcelable(ARGS_OPERATION) ?: Operation.SUM
    }

    private lateinit var viewModel: ComputeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Le view Model est rattaché au cycle de vie de l’activité
        // Tant que l’activité existe, le view model existe aussi
        // Quand l’activité se détruit, le view Model se détruit également
        viewModel = ViewModelProvider(this).get(ComputeViewModel::class.java)

        number1.addTextChangedListener(watcher)
        number2.addTextChangedListener(watcher)

        return inflater.inflate(R.layout.computation_fragment, container, false)
    }


    val watcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            compute()
        }
    }

    private fun compute() {
        nb1 = number1.toDouble() ?: return
        nb2 = number2.toDouble() ?: return
        viewModel.calculate(nb1, nb2, operation) //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submit.setOnClickListener {
           compute()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.resultatLiveData.observe(viewLifecycleOwner, Observer {
            resultLabel.text = getString(R.string.result, getString(R.string.sum), nb1, nb2, it)
        })
    }


    companion object {
        const val ARGS_OPERATION = "ARGS_OPERATION"
        fun newInstance(
            operation: Operation
        ): ComputationFragment {
            return ComputationFragment().apply {
                arguments = bundleOf(ARGS_OPERATION to operation)
            }
        }
    }
}

@Parcelize
enum class Operation : Parcelable {
    SUM, PRODUCT, MINUS, DIVISE
}