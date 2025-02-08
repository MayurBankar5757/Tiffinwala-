using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TWservice2.Models;

namespace TWservice2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class VendorController : Controller
    {
        private readonly p10_tiffinwalaContext _context;

        public VendorController(p10_tiffinwalaContext context)
        {
            _context = context;
        }

        [HttpGet]
        public List<Vendor> GetVendors()
        {
            return _context.Vendors
                .Include(v => v.UidNavigation)
                .ToList();
        }
        // Get Approved Vendors
        [HttpGet]
        public List<Vendor> GetApprovedVendors()
        {
            return _context.Vendors
                .Include(v => v.UidNavigation)
                .Where(v => v.IsVerified)
                .ToList();
        }

        // Get Disabled Vendors
        [HttpGet]
        public List<Vendor> GetDisabledVendors()
        {
            return _context.Vendors
                .Include(v => v.UidNavigation)
                .Where(v => !v.IsVerified)
                .ToList();
        }

        // Toggle verification status
        [HttpPut("{vendorId}")]
        public async Task<IActionResult> ToggleVendorVerification(int vendorId)
        {
            var vendor = await _context.Vendors.FindAsync(vendorId);
            if (vendor == null)
            {
                return NotFound();
            }

            vendor.IsVerified = !vendor.IsVerified;
            await _context.SaveChangesAsync();

            return Ok(vendor);
        }
        /*
        // Optional: Separate endpoints for approve/disable if needed
        [HttpPut("approve/{vendorId}")]
        public async Task<IActionResult> ApproveVendor(int vendorId)
        {
            var vendor = await _context.Vendors.FindAsync(vendorId);
            if (vendor == null)
            {
                return NotFound();
            }

            vendor.IsVerified = true;
            await _context.SaveChangesAsync();

            return Ok(vendor);
        }

        [HttpPut("disable/{vendorId}")]
        public async Task<IActionResult> DisableVendor(int vendorId)
        {
            var vendor = await _context.Vendors.FindAsync(vendorId);
            if (vendor == null)
            {
                return NotFound();
            }

            vendor.IsVerified = false;
            await _context.SaveChangesAsync();

            return Ok(vendor);
        }

        */
    }
}