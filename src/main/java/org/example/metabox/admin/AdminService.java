package org.example.metabox.admin;

import lombok.RequiredArgsConstructor;
import org.example.metabox._core.errors.exception.Exception401;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    //로그인
    public Admin login(AdminRequest.LoginDTO reqDTO){
        Admin admin = adminRepository.findByLoginIdAndPassword(reqDTO.getLoginId(),reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("존재 하지 않는 계정입니다."));

        return admin;
    }

}
