/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.easeui.domain;

public class ChatUserInfoModel {
    private static ChatUserInfoModel info = null;
    private ChatUserModel user;
    public static ChatUserInfoModel getInstance(){
        if(info==null){
            info=new ChatUserInfoModel();
        }
        return info;
    }

    public ChatUserModel getUser() {
        return user;
    }

    public void setUser(ChatUserModel user) {
        this.user = user;
    }
}
