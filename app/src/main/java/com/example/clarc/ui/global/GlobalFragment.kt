package com.example.clarc.ui.global

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.clarc.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_global.*
import org.json.JSONObject

class GlobalFragment : Fragment() {

    private lateinit var globalConfirmed: MaterialTextView
    private lateinit var globalActive: MaterialTextView
    private lateinit var globalRecovered: MaterialTextView
    private lateinit var globalDeceased: MaterialTextView
    private lateinit var globalText: MaterialTextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_global, container, false)
        globalConfirmed = root.findViewById(R.id.globalConfirmed)
        globalActive = root.findViewById(R.id.globalActive)
        globalRecovered = root.findViewById(R.id.globalRecovered)
        globalDeceased = root.findViewById(R.id.globalDeceased)
        globalText = root.findViewById(R.id.globalText)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        globalConfirmed.text = "Confirmed\n0"
        globalActive.text = "Active\n0"
        globalRecovered.text = "Recovered\n0"
        globalDeceased.text = "Deceased\n0"
        globalText.text = "response"
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.covid19india.org/data.json"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                val res = JSONObject(response).getJSONArray("statewise")
                globalText.text = "Response is: ${res.getJSONObject(0).get("active")}"
            },
            Response.ErrorListener { globalText.text = "That didn't work!" })

        queue.add(stringRequest)
    }
}