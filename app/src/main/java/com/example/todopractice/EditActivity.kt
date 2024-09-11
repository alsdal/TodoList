package com.example.todopractice

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.todopractice.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("할 일 추가하기")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // save버튼 눌렀을 때 입력한 데이터 인텐트에 담아 MainActivity에게 전달
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_add_save -> {
            val position = intent.getStringExtra("position")?.toInt()
            val inputData = binding.editEditView.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL(
                "UPDATE TODO_TB SET todo = ? WHERE ?",
                arrayOf(inputData, position)
            )
            db.close()

            // 메인 액티비티로 화면 전환, 입력 정보 전달
            val intent = intent
            intent.putExtra("result", inputData)
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }
}