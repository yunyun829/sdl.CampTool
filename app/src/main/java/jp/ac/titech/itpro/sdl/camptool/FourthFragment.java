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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FourthFragment extends Fragment {

    private ListView listView;
    private RecyclerView recyclerView;
    private String[] txt = {"aaa","bbb","cccc","dd"};
    private List<DiaryEntry> entries = new ArrayList<>() ;
    private File file;
    private String filename = "diary.json";
    public static final String NEW = "new";

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
        //entries = DiaryEntry.createListFromString(txt);
        entries = createListFromJson();
        DiaryEntry de = (DiaryEntry) getArguments().getSerializable(NEW);
        if(de != null){
            entries.add(de);
        }
        MyViewAdapter adapter = new MyViewAdapter(entries);
        createJsonFromList(entries);
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

    public List<DiaryEntry> createListFromJson() {
        List<DiaryEntry> list = new ArrayList<>();
        BufferedReader reader;

        String data = "";
        try {
            file = new File(getContext().getFilesDir(), filename);
            reader = new BufferedReader(new FileReader(file));
            String buff = reader.readLine();

            while (buff != null){
                data += buff;
                buff = reader.readLine();
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jArray = jsonObject.getJSONArray("diary");

            for (int i = 0; i < jArray.length(); i++){
                JSONObject entry = jArray.getJSONObject(i);
                DiaryEntry de = new DiaryEntry(entry.getString("title"),entry.getString("time"),entry.getString("contents"));
                list.add(de);
            }
        }catch (JSONException e){
            e.getStackTrace();
        }

        return list;
    }

    public void createJsonFromList(List<DiaryEntry> list){
        String filedata = "";
        try {
            JSONObject json ;
            JSONArray jArray =new JSONArray();
            for (int i = 0; i < list.size(); i++){
                json = new JSONObject();
                json.put("title",list.get(i).getTitle());
                json.put("time",list.get(i).getTime());
                json.put("contents",list.get(i).getContents());
                jArray.put(json);
            }
            JSONObject result = new JSONObject().put("diary",jArray);
            filedata = result.toString(2);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Log.d("Tag",filedata);
        try{
            file = new File(getContext().getFilesDir(), filename);
            FileWriter writer = new FileWriter(file);
            writer.write(filedata);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


