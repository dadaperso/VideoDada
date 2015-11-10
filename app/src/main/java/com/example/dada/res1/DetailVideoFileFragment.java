package com.example.dada.res1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import locdvdv3.DatabaseMedia;
import locdvdv3.Mapper;
import locdvdv3.VideoFile;


public class DetailVideoFileFragment extends DialogFragment
{
    private VideoFile mVideoFile;
    private Mapper mMapper;
    private Context mContext;

       @Override
    public void setArguments(Bundle args) {
        this.mMapper = (Mapper) args.getSerializable("mapper");
        this.mContext = getActivity();

        mVideoFile = DatabaseMedia.getInstance(this.mContext).getVideoFileByMapper(this.mMapper);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Recupération du Layout
        View view = inflater.inflate(R.layout.fragment_detail_video_file, null);


        // init value of TexView
        ((TextView)view.findViewById(R.id.txtDetailVideFilePath)).setText(mVideoFile.getPath());
        ((TextView)view.findViewById(R.id.txtDetailVideFileSize)).setText(mVideoFile.getFileSize()+"o");
        ((TextView)view.findViewById(R.id.txtDetailVideFileDuration)).setText(mVideoFile.getDuration()+"s");
        ((TextView)view.findViewById(R.id.txtDetailVideFileResolution))
                .setText(mVideoFile.getResolutionx() + "x" + mVideoFile.getResolutiony());
        ((TextView)view.findViewById(R.id.txtDetailVideFileContainer)).setText(mVideoFile.getContainerType());
        ((TextView)view.findViewById(R.id.txtDetailVideFileVideoCodec)).setText(mVideoFile.getVideoCodec());
        ((TextView)view.findViewById(R.id.txtDetailVideFileVideoBitrate)).setText(mVideoFile.getVideoBitrate()+"b/s");
        ((TextView)view.findViewById(R.id.txtDetailVideFileAudioCodec)).setText(mVideoFile.getAudioCodec());
        ((TextView)view.findViewById(R.id.txtDetailVideFileAudioBitrate)).setText(mVideoFile.getAudioBitrate()+"b/s");
        ((TextView)view.findViewById(R.id.txtDetailVideFileChannel))
                .setText(Integer.toString(mVideoFile.getChannel()));

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DetailVideoFileFragment.this.getDialog().dismiss();

                    }
                });

        return builder.create();

    }



}
