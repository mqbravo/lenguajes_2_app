DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;
/*
#Ref: https://dbtut.com/index.php/2018/10/01/column-level-encryption-with-pgcrypto-on-postgresql/
    #Encrypt: "PGP_SYM_ENCRYPT('The value to be entered in the column:','AES_KEY');
    #Decrypt: "PGP_SYM_DECRYPT(column_name::bytea,'AES_KEY');
*/

CREATE TABLE Users(id INT GENERATED ALWAYS AS IDENTITY,name VARCHAR,email VARCHAR,username VARCHAR,password VARCHAR);


DROP FUNCTION IF EXISTS spGetAllUsers;
CREATE FUNCTION spGetAllUsers()
RETURNS TABLE(ID INT,Name VARCHAR,Email VARCHAR,Username VARCHAR,Password VARCHAR(256)) AS $users$
	BEGIN
		RETURN QUERY SELECT U.id,U.name,U.email,U.username,U.password FROM Users U;
	END;
$users$ LANGUAGE plpgsql;

--select spGetAllUsers();

DROP FUNCTION IF EXISTS spLogin;
CREATE FUNCTION spLogin(usernameInput VARCHAR,passwordInput VARCHAR)
RETURNS BOOLEAN AS $$

	DECLARE
		usernameFound VARCHAR := (SELECT U.username FROM Users U WHERE U.username = usernameInput);
		decryptedPass VARCHAR := (SELECT PGP_SYM_DECRYPT(U.password::BYTEA,'AES_KEY') FROM Users U WHERE U.username = usernameInput);
	BEGIN
		
		IF usernameFound IS NOT NULL THEN
			IF (decryptedPass LIKE passwordInput) THEN
				RETURN TRUE;
			ELSE
				RETURN FALSE;
			END IF;
		ELSE
			BEGIN
				RETURN FALSE;
			END;
		END IF;
	END;
$$ LANGUAGE PLPGSQL;

--select * from spLogin('admin','admin');

DROP FUNCTION IF EXISTS spNewUser;
CREATE FUNCTION spNewUser(nameInput VARCHAR,emailInput VARCHAR,usernameInput VARCHAR,passwordInput VARCHAR)
RETURNS BOOLEAN AS $$
	DECLARE
		usernameFound VARCHAR(256) = (SELECT U.username FROM Users U WHERE U.username = usernameInput);
	BEGIN
		IF usernameFound IS NOT NULL THEN
			RETURN FALSE;
		ELSE
				INSERT INTO Users(name,email,username,password) VALUES(nameInput,emailInput,usernameInput,PGP_SYM_ENCRYPT(passwordInput,'AES_KEY'));
			RETURN TRUE;
		END IF;
		EXCEPTION WHEN OTHERS THEN
				RAISE EXCEPTION 'FAIL';
	END;
$$ LANGUAGE PLPGSQL;

SELECT * FROM spNewUser('Administrator','admin@correo.com','admin','admin');


/*
SELECT * FROM spNewUser('Administrador2','admi2n@correo.com','admin4','admin2');
insert into Users(name,email,username,password) VALUES ('Administrador','admin@correo.com','admin',PGP_SYM_ENCRYPT('admin','AES_KEY'));
*/

