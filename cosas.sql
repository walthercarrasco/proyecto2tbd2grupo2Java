CREATE EVENT `replicador`
	ON SCHEDULE
		EVERY 1 MINUTE
	ON COMPLETION NOT PRESERVE
	ENABLE
	COMMENT ''
	DO BEGIN
	SET @id := (SELECT id_bitacora FROM bitacora WHERE replicado=0 AND  tabla='Productos' LIMIT 1);
	SET @accion := (SELECT accion FROM bitacora WHERE id_bitacora=@id);
    SET @tabla := (SELECT tabla FROM bitacora WHERE id_bitacora=@id);
    SET @atributos := (SELECT atributos FROM bitacora WHERE id_bitacora=@id);
    SET @valores := (SELECT valores FROM bitacora WHERE id_bitacora=@id);
    SET @tabla_temp := CONCAT(@tabla,'_temp');
    SET @conn := (SELECT link FROM link_destino);
    CREATE TEMPORARY TABLE IF NOT EXISTS CONCAT(@tabla,'_temp') LIKE @tabla
    ENGGINE=CONNECT,
    TABLE_TYPE=ODBC,
    TABNAME=@tabla,
    CONNECTION=@conn;
    IF @accion = 'INSERT INTO' THEN
        INSERT INTO @tabla_temp (@atributos) VALUES (@valores);
        UPDATE bitacora SET replicado=1 WHERE id_bitacora=@id;
    ELSEIF @accion = 'UPDATE' THEN
        UPDATE @tabla_temp SET @atributos=@valores;
        UPDATE bitacora SET replicado=1 WHERE id_bitacora=@id;
    ELSEIF @accion = 'DELETE FROM' THEN
        DELETE FROM @tabla_temp WHERE @atributos=@valores;
        UPDATE bitacora SET replicado=1 WHERE id_bitacora=@id;
    END IF;
END