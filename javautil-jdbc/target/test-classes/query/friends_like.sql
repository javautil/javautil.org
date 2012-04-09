SELECT
	distinct name||' ('||phone||')'
FROM
	(
		SELECT
			name,
			phone,
			CASE
				WHEN name||' ('||phone LIKE UPPER(:search_text)||')' THEN 1
				WHEN name||' ('||phone LIKE UPPER(:search_text)||')%' THEN 2 
				WHEN name LIKE UPPER(:search_text) THEN 3
			 	WHEN phone LIKE UPPER(:search_text) THEN 4
				WHEN name LIKE UPPER(:search_text) || '%' THEN 5
				WHEN phone LIKE UPPER(:search_text) || '%' THEN 6
				ELSE 7
			END order_seq
		FROM
			friends
		WHERE
			name||' ('||phone||')' LIKE '%' || UPPER(:search_text) || '%' OR 
  			name LIKE '%' || UPPER(:search_text) || '%' OR
  			phone LIKE '%' || UPPER(:search_text) || '%'
		order by order_seq
	)
WHERE
	(
		(:number_of_rows IS NOT NULL AND ROWNUM <= :number_of_rows) OR
		(:number_of_rows IS NULL)
	)