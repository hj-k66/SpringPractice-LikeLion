package com.lion.dao;

import java.sql.Connection;

public interface ConnectionMaker {
    Connection makeConnection();
}
