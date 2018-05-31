package com.coolstardevil.autosilenttrigger

import android.content.Context
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat

class ValidationKotlin constructor(private val context: Context){

    fun checkFromTo(from:String?, to:String?) : Boolean {
        if (!from.isNullOrBlank() && !to.isNullOrBlank()) {
            val regex = Regex(pattern = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
            if (regex.matches(from!!) && regex.matches(to!!)) {
                val sdf = SimpleDateFormat("hh:mm")
                try {
                    val time_from = sdf.parse(from)
                    val time_to = sdf.parse(to)
                    if (time_from.before(time_to)) {
                        return true
                    } else {
                        Toast.makeText(this.context, "Start time cannot be greater than End time.", Toast.LENGTH_SHORT).show()
                        return false
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            else{
                Toast.makeText(this.context, "Please select proper time", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        Toast.makeText(this.context, "Start or End time cannot be null", Toast.LENGTH_SHORT).show()
        return false
    }
    fun checkStartEnd(start:String?, end:String?) : Boolean {
        if (!start.isNullOrBlank()&&!end.isNullOrBlank()) {
            val regex = Regex(pattern = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
            if (regex.matches(start!!)&& regex.matches(end!!)) {
                val sdf = SimpleDateFormat("hh:mm")
                try {
                    val time_start = sdf.parse(start)
                    val time_end = sdf.parse(end)
                    if (time_start.before(time_end)) {
                        return true
                    } else {
                        Toast.makeText(this.context, "Start time cannot be greater than End time.", Toast.LENGTH_SHORT).show()
                        return false
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            else{
                Toast.makeText(this.context, "Please select proper time", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        Toast.makeText(this.context, "Recess time cannot be null", Toast.LENGTH_SHORT).show()
        return false
    }

}
