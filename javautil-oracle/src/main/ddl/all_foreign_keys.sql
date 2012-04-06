create view all_foreign_keys as
	select 
 	ac.OWNER,  
    	 ac.CONSTRAINT_NAME,  
    	 ac.CONSTRAINT_TYPE,  
    	 ac.TABLE_NAME,  
    	 ac.SEARCH_CONDITION,  
    	 ac.R_OWNER,  
    	 ac.R_CONSTRAINT_NAME,  
    	 ac.DELETE_RULE,  
    	 ac.STATUS,  
    	 ac.DEFERRABLE,  
    	 ac.DEFERRED,  
    	 ac.VALIDATED,  
    	 ac.GENERATED,  
    	 ac.BAD,  
    	 ac.RELY,  
    	 ac.LAST_CHANGE,  
    	 ac.INDEX_OWNER,  
    	 ac.INDEX_NAME,  
    	 ac.INVALID,  
    	 ac.VIEW_RELATED,  
    	 acr.TABLE_NAME pk_table_name,  
    	 acr.SEARCH_CONDITION pk_search_conditioni,  
    	 acr.DELETE_RULE pk_delete_rule,  
    	 acr.STATUS pk_status,  
    	 acr.DEFERRABLE pk_deferrablle,  
    	 acr.DEFERRED pk_deferred,  
    	 acr.VALIDATED pk_validated,  
    	 acr.GENERATED pk_generated,  
    	 acr.BAD pk_bad,  
    	 acr.RELY pk_rely,  
    	 acr.LAST_CHANGE pk_last_change,  
    	 acr.INDEX_OWNER pk_index_owner,  
    	 acr.INDEX_NAME pk_index_name,  
    	 acr.INVALID pk_invalid,  
    	 acr.VIEW_RELATED pk_view_related  
    	  from all_constraints ac,  
    	     all_constraints acr  
    	  where  ac.r_constraint_name = acr.constraint_name and  
    	       ac.owner = acr.owner and  
    	       ac.constraint_type = 'R';


