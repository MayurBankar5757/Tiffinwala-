using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using TWservice2.DTO;
using TWservice2.Models;

namespace TWservice2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        [HttpGet]
        public List<User> GetUsers()
        {
            List<User> users = new List<User>();
            using (var db = new p10_tiffinwalaContext())
            {
                users = db.Users.ToList();
            }
            return users;
        }

        [Route("api/[controller]")]
        [ApiController]
        public class VendorController : ControllerBase
        {
            private readonly p10_tiffinwalaContext _context;

            public VendorController(p10_tiffinwalaContext context)
            {
                _context = context;
            }

            // GET: api/vendor/{uid}
            // Gets a single vendor's details by user ID (uid) where Rid == 2.
            [HttpGet("{uid}")]
            public async Task<IActionResult> GetVendor(int uid)
            {
                // Include the related Vendor information
                var user = await _context.Users
                    .Include(u => u.Vendors)
                    .FirstOrDefaultAsync(u => u.Uid == uid && u.Rid == 2);

                if (user == null)
                {
                    return NotFound("User not found or is not a vendor.");
                }

                // Assuming a vendor user has at least one Vendor record
                var vendor = user.Vendors.FirstOrDefault();
                if (vendor == null)
                {
                    return NotFound("Vendor details not found for this user.");
                }

                // Build the DTO using data from both User and Vendor
                var vendorDto = new VendorDto
                {
                    Uid = user.Uid,
                    Fname = user.Fname,
                    Lname = user.Lname,
                    Email = user.Email,
                    Rid = user.Rid,
                    Area = user.Area,
                    City = user.City,
                    Pincode = user.Pincode,
                    State = user.State,
                    IsVerified = vendor.IsVerified,
                    AdharNo = vendor.AdharNo,
                    FoodLicenceNo = vendor.FoodLicenceNo
                };

                return Ok(vendorDto);
            }

            // GET: api/vendor
            // Gets all vendor users (users with Rid == 2) along with their vendor details.
            [HttpGet]
            public async Task<IActionResult> GetAllVendors()
            {
                var vendorUsers = await _context.Users
                    .Where(u => u.Rid == 2)
                    .Include(u => u.Vendors)
                    .ToListAsync();

                var vendorDtos = vendorUsers
                    .Select(u =>
                    {
                        // Use the first vendor record if available
                        var vendor = u.Vendors.FirstOrDefault();
                        if (vendor == null) return null;

                        return new VendorDto
                        {
                            Uid = u.Uid,
                            Fname = u.Fname,
                            Lname = u.Lname,
                            Email = u.Email,
                            Rid = u.Rid,
                            Area = u.Area,
                            City = u.City,
                            Pincode = u.Pincode,
                            State = u.State,
                            IsVerified = vendor.IsVerified,
                            AdharNo = vendor.AdharNo,
                            FoodLicenceNo = vendor.FoodLicenceNo
                        };
                    })
                    .Where(dto => dto != null)
                    .ToList();

                return Ok(vendorDtos);
            }
        }


    }
}
