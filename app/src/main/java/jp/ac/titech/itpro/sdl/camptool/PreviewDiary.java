package jp.ac.titech.itpro.sdl.camptool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class PreviewDiary extends Fragment {

    public static final String ENTRY = "entry";

    private DiaryEntry de;
    private TextView title,contents;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.preview_diary, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        de=(DiaryEntry) getArguments().getSerializable(ENTRY);

        title = getView().findViewById(R.id.edit_text);
        contents = getView().findViewById(R.id.edit_text2);

        title.setText(de.getTitle());
        contents.setText(de.getContents());
        getView().findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PreviewDiary.this).navigate(R.id.action_previewDiary_to_fourthFragment);
            }
        });

    }


    public static PreviewDiary newInstance(DiaryEntry de) {
        PreviewDiary fragment = new PreviewDiary();

        Bundle args = new Bundle();
        args.putSerializable(ENTRY,de);
        fragment.setArguments(args);

        return fragment;
    }
}
