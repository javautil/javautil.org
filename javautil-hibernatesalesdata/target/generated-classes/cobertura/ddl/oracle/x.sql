select trunc(dbms_random.value(100000,999999)) from all_tab_columns where rownum < 100;
