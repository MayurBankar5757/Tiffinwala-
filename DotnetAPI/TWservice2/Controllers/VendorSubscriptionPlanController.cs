using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using TWservice2.Models;

namespace TWservice2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class VendorSubscriptionPlanController : ControllerBase
    {
        private readonly p10_tiffinwalaContext _context;

        public VendorSubscriptionPlanController(p10_tiffinwalaContext context)
        {
            _context = context;
        }

        [HttpGet]
        public List<VendorSubscriptionPlan> GetVendorPlans()
        {
            return _context.VendorSubscriptionPlans.ToList();
        }

        [HttpGet("{id}")]
        public IActionResult GetImage(int id)
        {
            var plan = _context.VendorSubscriptionPlans.FirstOrDefault(v => v.PlanId == id);
            if (plan == null || plan.Image == null)
            {
                return NotFound("Image not found.");
            }

            return File(plan.Image, "image/jpeg"); // Change MIME type if needed
        }

        [HttpPost("{id}")]
        public async Task<IActionResult> UploadImage(int id, IFormFile file)
        {
            if (file == null || file.Length == 0)
                return BadRequest("Invalid file.");

            using var memoryStream = new MemoryStream();
            await file.CopyToAsync(memoryStream);
            var imageData = memoryStream.ToArray();

            var plan = _context.VendorSubscriptionPlans.FirstOrDefault(v => v.PlanId == id);
            if (plan == null)
                return NotFound("Plan not found.");

            plan.Image = imageData;
            _context.SaveChanges();

            return Ok("Image uploaded successfully.");
        }
    }
}
