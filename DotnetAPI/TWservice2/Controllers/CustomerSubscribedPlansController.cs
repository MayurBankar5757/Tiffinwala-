using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using TWservice2.Models;

namespace TWservice2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class CustomerSubscribedPlansController : ControllerBase

    {
        [HttpGet]
        public List<CustomerSubscribedPlan> GetCustsubs()
        {
            List<CustomerSubscribedPlan> custsubs = new List<CustomerSubscribedPlan>();
            using (var db = new p10_tiffinwalaContext())
            {
                custsubs = db.CustomerSubscribedPlans.ToList();
            }
            return custsubs;
        }

    }



}
