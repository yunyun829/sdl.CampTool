package jp.ac.titech.itpro.sdl.camptool;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FourthFragment extends Fragment {

    private ListView listView;
    private RecyclerView recyclerView;
    private String[] txt = {"aaa","bbb","cccc","dd"};
    private List<DiaryEntry> entries = new ArrayList<>() ;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //listView = getView().findViewById(R.id.list_view);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,txt);
        //listView.setAdapter(arrayAdapter);
        recyclerView = getView().findViewById(R.id.recycle_view);
        entries = DiaryEntry.createListFromString(txt);
        MyViewAdapter adapter = new MyViewAdapter(entries);
        adapter.setEvent(new MyViewAdapter.onClickEvent() {
                             @Override
                             public void onClickAction(int position) {
                                 //PreviewDiary pd = PreviewDiary.newInstance(entries.get(position));
                                 //FragmentManager fm = getFragmentManager();
                                 //FragmentTransaction ft = fm.beginTransaction();
                                 //ft.replace(R.id.container,pd);
                                 Log.d("","click item"+entries.get(position).getTitle());
                                 Bundle bundle = new Bundle();
                                 bundle.putSerializable(PreviewDiary.ENTRY,entries.get(position));
                                 NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_previewDiary,bundle);
                             }
                         }
        );
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        FloatingActionButton toEdit = getView().findViewById(R.id.new_diary_button);
        toEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_editDiary);
            }
        });
    }
}


