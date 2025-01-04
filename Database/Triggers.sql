DELIMITER $$

CREATE TRIGGER after_user_insert
AFTER INSERT ON `user`
FOR EACH ROW
BEGIN
    -- Check if the role assigned to the user is 'Vendor'
    IF NEW.Rid = (SELECT Role_Id FROM `role` WHERE Role_Name = 'Vendor') THEN
        -- Insert a new record into the 'vendor' table with 'is_verified' set to 0
        INSERT INTO `vendor` (Uid, Is_verified) 
        VALUES (NEW.Uid, 0);
    END IF;
END $$

DELIMITER ;
