package jp.ac.titech.itpro.sdl.camptool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class EditDiary extends Fragment {
    private Button save ,quit;
    private EditText title, contents;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        save = getView().findViewById(R.id.save_button);
        quit = getView().findViewById(R.id.back_button);
        title = getView().findViewById(R.id.edit_text);
        contents = getView().findViewById(R.id.edit_text2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = title.getText().toString();
                String newContents = contents.getText().toString();
                DiaryEntry newDE = new DiaryEntry(newTitle,newContents);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FourthFragment.NEW,newDE);
                NavHostFragment.findNavController(EditDiary.this).navigate(R.id.action_editDiary_to_fourthFragment,bundle);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(EditDiary.this).navigate(R.id.action_editDiary_to_fourthFragment);
            }
        });
    }
}
