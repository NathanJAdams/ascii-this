package users.security;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class PublicKeysTest {
    @Test
    public void testParsingKeys() {
        String keys = "{\"keys\":[{\"alg\":\"RS256\",\"e\":\"AQAB\",\"kid\":\"pZfPzeF//6ulswmQISjDAm0rhXiJRYtGY27eDPvU73k=\",\"kty\":\"RSA\",\"n\":\"kRmjx4e1U4hKH-M_Gz2sGFlr-RQxUkrRDGDnSh_WtyhVMb7CIfasPsanV1da99wuGwQCuw5NbfUqAlnD9X6BV6D2ghpJUR4QrNLBgAQTTkiNe2xJFdsQjICQGxyEckmBSKuQ_E7dULl3R4DRsf_Klea_OdD2wnjfYBXCZNl63iehPPj13UxwWxLjo6pWGMczSkRmaiD0wkSr-ANpSVYuP0ew1Siv10N-Fr_uSoVGjcrnIfpgq5HcVCeGhCdALgRrowH-ch4-9MzrXJBW-h9Lh5-jFYkEN6KTRo2vJ8JCgLxXIpRx6EEcRCAB-qElLUK0qHpetSc5cHyL6v58qlk2pQ\",\"use\":\"sig\"},{\"alg\":\"RS256\",\"e\":\"AQAB\",\"kid\":\"HYHjhqT19jgM84jtEQEUkPR1QuQpOSUDOyaUw54slMo=\",\"kty\":\"RSA\",\"n\":\"qV9zg0OcU2sAsRVrRMkhzCt0OKj_iv23Djg4btmeTKHQ9bueRb-5_7Zp5Hbc6vkmFuZ3i_G80IC7Ir_O3Bd5K8LQCieKvc15Ic29YULtb4Jooa2-U_FbNNEmwDIkBLfL4Mpt79g1jWlpbTHVjgbWkyLirbltFQ_f9G2ArHj-ZrjEr_t0g2H8ziiQRnAeteYbkGyNXz1lhbV2E_MNvO_ykjLPctuApf5xRXLqowTK_grZTXGT4mpZoJluDM10CZADJIbD64RvZUgSwoDGPr3Jjza-2bhjmMLMiJOWZ4aQ6m1vdcVZ_p7BFMIlCsNjlBBSD9mxj6mHH-c1WTpxGSBfdQ\",\"use\":\"sig\"}]}";
        PublicKeys publicKeys = new Gson().fromJson(keys, PublicKeys.class);
        Assert.assertNotNull(publicKeys);
        System.out.println(publicKeys);
    }
}
