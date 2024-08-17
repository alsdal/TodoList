package com.example.todopractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todopractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사후처리 위한 ActivityResultLauncher
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            // Contract
            ActivityResultContracts.StartActivityForResult()){
            // Callback
            it.data!!.getStringExtra("result")?.let {
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }
        // launch
        binding.mainFab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
                requestLauncher.launch(intent)
        }

        // 상태값 있다면(번들 객체 널이 아닌 경우) 목록에 반영.
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        }?: let {
            // Null 아닐 경우 목록 데이터 초기화
            mutableListOf<String>()
        }

        // 화면 구성
        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager=layoutManager
        adapter=MyAdapter(datas)
        binding.mainRecyclerView.adapter=adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    // 상태 데이터 유지
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }


}