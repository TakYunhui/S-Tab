package com.sixb.note.dto.note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteRequestDto {
    private String parentFolderId;
    private String title;
    private int color;
    private int template;
    private int direction;
}
