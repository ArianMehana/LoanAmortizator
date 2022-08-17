package com.example.loanamortizator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var shumaEditText: EditText
    lateinit var interesiEditText: EditText
    lateinit var kohezgjatjaEditText: EditText
    lateinit var kestiTextView: TextView
    lateinit var totaliTextView: TextView
    lateinit var kalkuloButton: Button
    lateinit var recyclerView: RecyclerView
    lateinit var variableList: MutableList<Variables>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            initUI()


    }

    private fun initUI(){
       shumaEditText = findViewById(R.id.shuma_edittext)
       interesiEditText = findViewById(R.id.interesi_edittext)
       kohezgjatjaEditText = findViewById(R.id.kohezgjatja_edittext)
       kestiTextView = findViewById(R.id.kesti)
       totaliTextView = findViewById(R.id.totali)
       kalkuloButton = findViewById(R.id.kalkulo_button)
        recyclerView = findViewById(R.id.recycleView)


        kalkuloButton.setOnClickListener {

            amortizoKestin()
            val adapter = LoanAdapter(variableList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.UP
            kalkuloButton.setOnClickListener{
                kestiTextView.text = "Kesti: ${df.format(calculateMonthlyPayments())}"
                totaliTextView.text = "Totali: ${df.format(kalkuloTotalin())}"
            }
        }

    }


    private fun getShuma() = shumaEditText.text.toString().toDouble()
    private fun getInteresi() = interesiEditText.text.toString().toDouble() /1200
    private fun getKohezgjatja() = kohezgjatjaEditText.text.toString().toDouble()

    //pv = shuma, i = interesi, n = muajt -------- returns payment per month or a double
    private fun calculateMonthlyPayments() : Double{
        val PV = getShuma()
        val m = MonthlyPayment()
        return PV*m
    }

    private fun MonthlyPayment(): Double{
        val i = getInteresi()
        val n = getKohezgjatja()
        return (i*Math.pow((1+i), n)) / (Math.pow((1+i), n) - 1)
    }
    private fun kalkuloTotalin(): Double{
        val PMT = calculateMonthlyPayments()
        val n = getKohezgjatja()
        return PMT * n
    }


    private fun amortizoKestin() : Void?{

    val PMT = calculateMonthlyPayments()
    val int = getInteresi()
    val n = getKohezgjatja()
    var bilanci = kalkuloTotalin()
    variableList = mutableListOf<Variables>()

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.UP

        val mf = DecimalFormat("#")

    for (i in 1..n.toInt())
  {

        val principal = (PMT*(1/((Math.pow((1+int).toDouble(), i.toDouble())))))
        val interesiAmort = PMT - principal
        val pagesa = interesiAmort + principal
        val Bilanci = bilanci - pagesa
        bilanci = Bilanci
      if(bilanci<1 || bilanci<0)
          bilanci= 0.0
      var newVariables =Variables(mf.format(i), df.format(pagesa).toDouble(),
          df.format(interesiAmort).toDouble(), df.format(principal).toDouble(),df.format(bilanci).toDouble() )
      variableList.add(newVariables)
   }
    return null
   }


}