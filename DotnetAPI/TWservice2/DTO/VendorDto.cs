namespace TWservice2.DTO
{
    public class VendorDto
    {
       
            public int Uid { get; set; }
            public string Fname { get; set; } = null!;
            public string Lname { get; set; } = null!;
            public string Email { get; set; } = null!;
            public int Rid { get; set; }
            public string? Area { get; set; }
            public string? City { get; set; }
            public string? Pincode { get; set; }
            public string? State { get; set; }
            public bool IsVerified { get; set; }
            public string? AdharNo { get; set; }
            public string? FoodLicenceNo { get; set; }
       
    }
}
