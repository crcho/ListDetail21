package com.sample.listdetail;

//Adapter의 ViewHolder에서 Activity로 이벤트를 보내기 위한 item click listener 성격의 interface.
public interface ItemClickHandler {
    void itemClickEvent(int position, String value);
}
