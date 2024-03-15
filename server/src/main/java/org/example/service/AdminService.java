package org.example.service;

import org.example.dto.*;
import org.example.entity.Admin;


public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDTO);
}
