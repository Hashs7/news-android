package me.sebhernoux.dmii.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.choice_fragment.*
import me.sebhernoux.dmii.R

class ChoiceFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(button1, button2, button3, button4).forEach {
            it.setOnClickListener {button ->
                showComputation((button as MaterialButton).text.toString())
            }
        }
    }

    private fun showComputation(operator: String) {
        val op: Operation = when(operator) {
            "+" -> Operation.SUM
            "-" -> Operation.MINUS
            "x" -> Operation.PRODUCT
            "/" -> Operation.DIVISE
            else-> Operation.SUM
        }
        changeFragment(op)
    }

    private fun changeFragment(operator: Operation) {
        val fragment = ComputationFragment.newInstance(operator)
        // créer un transaction sur le fragment manager

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            //replacer le précédent fragment, s'il existe

            replace(R.id.fragment_container, fragment)
            //ajouter la transaction dans la stack
            addToBackStack(null)
        }?.commit()
    }

}
